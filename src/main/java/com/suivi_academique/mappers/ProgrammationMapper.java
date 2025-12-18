package com.suivi_academique.mappers;

import com.suivi_academique.dto.ProgrammationDTO;
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.dto.CoursDTO;
import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.entities.Programmation;
import com.suivi_academique.entities.Salle;
import com.suivi_academique.entities.Cours;
import com.suivi_academique.entities.Personnel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor // Pour l'injection des Mappers des dépendances
public class ProgrammationMapper {

    // Injection des mappers des dépendances
    private final SalleMapper salleMapper;
    private final CoursMapper coursMapper;
    private final PersonnelMapper personnelMapper;

    public ProgrammationDTO toDTO(Programmation programmation) {
        ProgrammationDTO dto = new ProgrammationDTO();

        // Champs simples
        dto.setId(programmation.getId());
        dto.setNbHeure(programmation.getNbHeure());
        dto.setDateProgrammation(programmation.getDateProgrammation());
        dto.setFinProgrammation(programmation.getFinProgrammation());
        dto.setStatutProgrammation(programmation.getStatutProgrammation());

        // Relations complexes (Mapping des Entités vers les DTOs)
        dto.setCours(coursMapper.toDTO(programmation.getCours()));
        dto.setSalle(salleMapper.toDTO(programmation.getSalle()));

        // Deux instances de Personnel (Personnel Prog et Personnel Val)
        dto.setPersonnelProg(personnelMapper.toDTO(programmation.getPersonnelProg()));
        dto.setPersonnelVal(personnelMapper.toDTO(programmation.getPersonnelVal()));

        return dto;
    }

    public Programmation toEntity(ProgrammationDTO programmationDTO) {

        // Conversion des DTOs imbriqués en Entités (pour la persistance)
        Salle salle = salleMapper.toEntity(programmationDTO.getSalle());
        Cours cours = coursMapper.toEntity(programmationDTO.getCours());

        // Conversion des deux instances de Personnel
        Personnel personnelProg = personnelMapper.toEntity(programmationDTO.getPersonnelProg());
        Personnel personnelVal = personnelMapper.toEntity(programmationDTO.getPersonnelVal());

        // Création de l'entité Programmation
        // NOTE: Si vous n'avez pas de constructeur spécifique, utilisez le constructeur
        // généré par Lombok @AllArgsConstructor (assurez-vous de l'ordre exact des champs dans l'entité)

        return new Programmation(
                programmationDTO.getId(),
                programmationDTO.getNbHeure(),
                programmationDTO.getDateProgrammation(),
                programmationDTO.getFinProgrammation(),
                programmationDTO.getStatutProgrammation(),

                salle,
                cours,
                personnelProg,
                personnelVal
                // Ajoutez ici toutes les autres listes de relations @OneToMany si elles existent
        );
    }
}