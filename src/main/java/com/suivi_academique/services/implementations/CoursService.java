package com.suivi_academique.services.implementations;

import com.suivi_academique.dto.CoursDTO;
import com.suivi_academique.entities.Cours;
import com.suivi_academique.mappers.CoursMapper;
import com.suivi_academique.repositories.CoursRepository;
import com.suivi_academique.services.interfaces.CoursInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class CoursService implements CoursInterface {

    private CoursRepository coursRepository;

    private CoursMapper coursMapper;

    @Override
    public CoursDTO save(CoursDTO coursDTO) {
        if(coursDTO.getCodeCours().isEmpty()){

            throw new RuntimeException("Données incorret");

        }else{
            Cours cours = coursRepository.save(coursMapper.toEntity(coursDTO));
            return coursMapper.toDTO(cours);
        }
    }


    @Override
    public List<CoursDTO> getAll() {
        return coursRepository.findAll().stream().map(
                coursMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CoursDTO getById(String codeCours) {
        Cours cours = coursRepository.findById(codeCours).get();
        if(cours==null){
            throw new RuntimeException("Salle non trouvée");
        }else{
            return coursMapper.toDTO(cours);
        }
    }

    @Override
    public CoursDTO update(String codeCours, CoursDTO coursDTO) {
        Cours cours = coursRepository.findById(codeCours).get();

        if(cours==null){
            throw new RuntimeException("Cours introuvable");
        }else{
            cours.setDescCours(coursDTO.getDescCours());
            cours.setLabelCours(coursDTO.getLabelCours());
            cours.setNbCreditCours(coursDTO.getNbCreditCours());
            cours.setNbHeureCours(coursDTO.getNbHeureCours());
            coursRepository.save(cours);
            return coursMapper.toDTO(cours);

        }
    }

    @Override
    public void delete(String codeCours) {

        if(!coursRepository.existsById(codeCours)){
            throw new RuntimeException("impossible de supprimer cours introuvable");
        }
        coursRepository.deleteById(codeCours);

    }
}
