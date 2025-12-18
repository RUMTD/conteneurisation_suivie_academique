package com.suivi_academique.services.implementations;

import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.entities.Personnel;
import com.suivi_academique.mappers.PersonnelMapper;
import com.suivi_academique.repositories.PersonnelRepository;
import com.suivi_academique.services.interfaces.Personnelnterface;
import com.suivi_academique.utils.CodeGenerator;
import com.suivi_academique.utils.RolePersonnel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonnelService implements Personnelnterface {

        private PersonnelRepository personnelRepository;

        private PersonnelMapper personnelMapper;

        private CodeGenerator codeGenerator;
    private PasswordEncoder passwordEncoder;


    @Override
    public List<PersonnelDTO> getAll() {
        return personnelRepository.findAll().stream().map(
                personnelMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public PersonnelDTO getById(String codePersonnel) {
        Personnel personnel = personnelRepository.findById(codePersonnel).get();
        if(personnel==null){
            throw new RuntimeException("Personnel non trouvée");
        }else{
            return personnelMapper.toDTO(personnel);
        }
    }

    @Override
    public PersonnelDTO update(String codePersonnel, PersonnelDTO personnelDTO) {
        Personnel personnel = personnelRepository.findById(codePersonnel).get();

        if(personnel==null){
            throw new RuntimeException("Cours introuvable");
        }else{
            personnel.setNomPersonnel(personnelDTO.getNomPersonnel());
            personnel.setLoginPersonnel(personnelDTO.getLoginPersonnel());
            personnel.setPadPersonnel(personnelDTO.getPadPersonnel());
            personnel.setPhonePersonnel(personnelDTO.getPhonePersonnel());
            personnel.setRolePersonnel(personnelDTO.getRolePersonnel());
            personnel.setSexePersonnel(personnelDTO.getSexePersonnel());
            personnelRepository.save(personnel);
            return personnelMapper.toDTO(personnel);

        }
    }

    @Override
    public PersonnelDTO getByRole(String roleString) {
        return personnelRepository.searchByRole(roleString).stream().map(
                personnelMapper::toDTO).collect(Collectors.toList()).get(0);
    }

    @Override
    public void delete(String codePersonnel) {
        boolean exist = personnelRepository.existsById(codePersonnel);

        if (!exist) {
            // Cette erreur devrait renvoyer 400 Bad Request
            throw new RuntimeException("Personnel inexistant.");
        } else {
            // C'est ici que l'échec se produit probablement
            personnelRepository.deleteById(codePersonnel);
        }

    }

    @Override
    public PersonnelDTO save(PersonnelDTO personnelDTO) {
        // Generate code based on role
        String generatedCode = codeGenerator.generate(personnelDTO.getRolePersonnel().name());
        if (generatedCode == null) {
            throw new RuntimeException("Impossible de générer un code pour le rôle: " + personnelDTO.getRolePersonnel());
        }
        personnelDTO.setCodePersonnel(generatedCode);

        // Convert DTO to entity and ensure code is set
        Personnel personnel = new Personnel();
        personnel.setCodePersonnel(generatedCode);
        personnel.setNomPersonnel(personnelDTO.getNomPersonnel());
        personnel.setLoginPersonnel(personnelDTO.getLoginPersonnel());
        personnel.setPadPersonnel(passwordEncoder.encode(personnelDTO.getPadPersonnel()));
        personnel.setSexePersonnel(personnelDTO.getSexePersonnel());
        personnel.setPhonePersonnel(personnelDTO.getPhonePersonnel());
        if (personnelDTO.getRolePersonnel() != null) {
            personnel.setRolePersonnel(RolePersonnel.valueOf(personnelDTO.getRolePersonnel().name()));
        }

        Personnel savedPersonnel = personnelRepository.save(personnel);
        return personnelMapper.toDTO(savedPersonnel);
    }
}

