package com.suivi_academique.services.implementations;

import com.suivi_academique.dto.AffectationDTO;
import com.suivi_academique.entities.Affectation;
import com.suivi_academique.entities.AffectationId;
import com.suivi_academique.mappers.AffectationMapper;
import com.suivi_academique.mappers.CoursMapper;
import com.suivi_academique.mappers.PersonnelMapper;
import com.suivi_academique.repositories.AffectationRepository;
import com.suivi_academique.repositories.CoursRepository;
import com.suivi_academique.repositories.PersonnelRepository;
import com.suivi_academique.services.interfaces.AffectationInterface;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AffectationService implements AffectationInterface {



    private AffectationRepository affectationRepository;

    private AffectationMapper affectationMapper;

    private CoursRepository coursRepository;

    private PersonnelRepository personnelRepository;

    private PersonnelMapper personnelMapper;

    private CoursMapper coursMapper;


    @Override
    public AffectationDTO save(AffectationDTO affectationDTO) {

        String codeCours = affectationDTO.getCours().getCodeCours();
        String codePersonnel = affectationDTO.getPersonnel().getCodePersonnel();

        if (!coursRepository.existsById(codeCours)) {
            throw new RuntimeException("Le Cours avec l'ID " + codeCours + " n'existe pas.");
        }
        if (!personnelRepository.existsById(codePersonnel)) {
            throw new RuntimeException("Le Personnel avec l'ID " + codePersonnel + " n'existe pas.");
        }

        AffectationId affectationId = new AffectationId(codeCours, codePersonnel);


        if (affectationRepository.existsById(affectationId)) {
            throw new RuntimeException("Cette Affectation existe déjà pour ce Cours et ce Personnel.");
        }

        // Assignation de l'ID composite au DTO avant le mapping
        // Ceci est nécessaire pour que le mapper (toEntity) puisse créer l'entité complète
        affectationDTO.setCodeAffectation(affectationId);

        Affectation affectation = affectationRepository.save(affectationMapper.toEntity(affectationDTO));
        return affectationMapper.toDTO(affectation);
    }

    @Override
    public List<AffectationDTO> getAll() {
        return affectationRepository.findAll().stream().map(
                affectationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AffectationDTO getById(AffectationId affectationId) {

        Affectation affectation = affectationRepository.findById(affectationId).orElse(null);
        if (affectation == null) {
            throw new RuntimeException("Affectation non trouvé");
        }else{
            return  affectationMapper.toDTO(affectation);
        }

    }

    @Override
    public AffectationDTO update(AffectationId affectationId, AffectationDTO affectationDTO) {
    Affectation affectation = affectationRepository.findById(affectationId).orElse(null);
        if(affectation == null){
            throw new RuntimeException("AffectationId non trouvé");
        }
        affectation.setPersonnel(personnelMapper.toEntity(affectationDTO.getPersonnel()));
        affectation.setCours(coursMapper.toEntity(affectationDTO.getCours()));
        affectationRepository.save(affectation);
        return affectationMapper.toDTO(affectation);
    }

    @Override
    public void delete(String codeCours, String codePersonnel ) {
        AffectationId id = new AffectationId(codeCours, codePersonnel);

        boolean exist = affectationRepository.existsById(id);

        if (!exist) {
            throw new RuntimeException("Affectation inexistante pour la suppression.");
        } else {
            affectationRepository.deleteById(id);
        }
    }
}
