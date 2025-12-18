package com.suivi_academique.services.implementations;


import com.suivi_academique.dto.AuthRequest;
import com.suivi_academique.dto.AuthResponse;
import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.entities.Personnel;
import com.suivi_academique.repositories.PersonnelRepository;
import com.suivi_academique.config.JwtUtil;
import com.suivi_academique.services.interfaces.AuthentificationInterface;
import com.suivi_academique.utils.CodeGenerator;
import com.suivi_academique.utils.RolePersonnel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthentificationService implements AuthentificationInterface {

        private final PersonnelRepository personnelRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtil jwtUtil;
        private final AuthenticationManager authenticationManager;
        private final CodeGenerator codeGenerator;


        public AuthResponse authenticate(PersonnelDTO request){


            Personnel p = personnelRepository.findByLoginPersonnel(request.getLoginPersonnel())
                    .orElseThrow(() -> new RuntimeException("Personnel non trouvé"));



            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLoginPersonnel(),
                            request.getPadPersonnel()
                    )
            );

            String jwtToken = jwtUtil.generateToken(p);

            return AuthResponse.builder()
                    .token(jwtToken)
                    .codePersonnel(p.getCodePersonnel())
                    .nomPersonnel(p.getNomPersonnel())
                    .rolePersonnel(p.getRolePersonnel().name())
                    .build();
        }

        public AuthResponse register(PersonnelDTO personnelDTO){

            if (personnelDTO.getPadPersonnel() == null || personnelDTO.getPadPersonnel().isEmpty()) {
                throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
            }

            // Generate code based on role
            String generatedCode = codeGenerator.generate(personnelDTO.getRolePersonnel().name());
            if (generatedCode == null) {
                throw new RuntimeException("Impossible de générer un code pour le rôle: " + personnelDTO.getRolePersonnel());
            }
            personnelDTO.setCodePersonnel(generatedCode);

            // Convert DTO to entity
            Personnel personnel = new Personnel();
            personnel.setCodePersonnel(generatedCode);
            personnel.setNomPersonnel(personnelDTO.getNomPersonnel());
            personnel.setLoginPersonnel(personnelDTO.getLoginPersonnel());
            personnel.setSexePersonnel(personnelDTO.getSexePersonnel());

            // Secure role conversion
            try {
                // CORRECTION: Si personnelDTO.getRolePersonnel() retourne RolePersonnel, on utilise .toString()
                // pour appeler .toUpperCase() sur un String, puis .valueOf() sur le résultat.
                // Si getRolePersonnel retourne String, .toString() est superflu mais inoffensif.
                personnel.setRolePersonnel(RolePersonnel.valueOf(personnelDTO.getRolePersonnel().toString().toUpperCase()));
            } catch (Exception e){
                throw new IllegalArgumentException("Rôle invalide : " + personnelDTO.getRolePersonnel());
            }

            // Encode password
            personnel.setPadPersonnel(passwordEncoder.encode(personnelDTO.getPadPersonnel()));

            // Save
            Personnel savedPersonnel = personnelRepository.save(personnel);

            // Generate JWT
            String jwtToken = jwtUtil.generateToken(savedPersonnel);

            return AuthResponse.builder()
                    .token(jwtToken)
                    .codePersonnel(savedPersonnel.getCodePersonnel())
                    .nomPersonnel(savedPersonnel.getNomPersonnel())
                    .rolePersonnel(savedPersonnel.getRolePersonnel().name())
                    .build();
        }
}

