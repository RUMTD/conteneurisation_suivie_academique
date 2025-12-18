package com.suivi_academique.mappers;

import com.suivi_academique.dto.AffectationDTO;
import com.suivi_academique.dto.CoursDTO;
import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.entities.Affectation;
import com.suivi_academique.entities.AffectationId;
import com.suivi_academique.entities.Cours;
import com.suivi_academique.entities.Personnel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor // Pour l'injection automatique des dépendances
public class AffectationMapper {

    // Injection des mappers pour les entités liées
    private final PersonnelMapper personnelMapper;
    private final CoursMapper coursMapper;

    public AffectationDTO toDTO(Affectation affectation) {
        // Mappe les entités Personnel et Cours vers leurs DTOs
        PersonnelDTO personnelDTO = personnelMapper.toDTO(affectation.getPersonnel());
        CoursDTO coursDTO = coursMapper.toDTO(affectation.getCours());

        // Supposons que l'entité Affectation a un getter pour l'ID composite.
        AffectationId id = affectation.getCodeAffectation();

        return new AffectationDTO(
                personnelDTO,
                coursDTO,
                id // Inclut la clé composite dans le DTO
        );
    }

    public Affectation toEntity(AffectationDTO affectationDTO) {

        // 1. Conversion des DTOs imbriqués en Entités
        Personnel personnel = personnelMapper.toEntity(affectationDTO.getPersonnel());
        Cours cours = coursMapper.toEntity(affectationDTO.getCours());

        // 2. Récupération/Construction de l'ID composite
        // NOTE: Lors de la CRÉATION, l'ID composite pourrait être null dans le DTO,
        // ou vous pourriez le construire ici à partir des IDs des entités.
        // Par simplicité, on utilise la version incluse si elle est présente.
        AffectationId affectationId = affectationDTO.getCodeAffectation();

        // 3. Création de l'entité Affectation
        // L'entité Affectation doit avoir un constructeur qui prend toutes les dépendances.
        // Si vous utilisez @AllArgsConstructor sur l'entité Affectation, l'ordre est important.
        // Supposons que l'ordre des champs dans Affectation.java est : AffectationId, Personnel, Cours

        return new Affectation(
                affectationId, // Le 1er argument (l'ID composite)
                personnel,     // Le 2e argument (Personnel)
                cours          // Le 3e argument (Cours)
                // Ajoutez d'autres champs si Affectation en possède
        );
    }
}