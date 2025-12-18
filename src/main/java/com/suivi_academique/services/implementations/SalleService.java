package com.suivi_academique.services.implementations;

import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.entities.Salle;
import com.suivi_academique.mappers.SalleMapper;
import com.suivi_academique.repositories.SalleRepository;
import com.suivi_academique.services.interfaces.SalleInterface;
import com.suivi_academique.utils.SalleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
@AllArgsConstructor
public class SalleService implements SalleInterface {

    private SalleRepository salleRepository;

    private SalleMapper salleMapper;

    private static final Logger log = LoggerFactory.getLogger(SalleService.class);


    @Override
    public SalleDTO save(SalleDTO salleDTO){

        if(salleDTO.getCodeSalle().isEmpty() || salleDTO.getContenace()<10){

            throw new RuntimeException("Données incorret");

        }else{
            Salle salle = salleRepository.save(salleMapper.toEntity(salleDTO));
            return salleMapper.toDTO(salle);
        }
    }

    @Override
    public List<SalleDTO> getAll() {
        return salleRepository.findAll().stream().map(
        salleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SalleDTO getById(String codeSalle) {
        Salle salle = salleRepository.findById(codeSalle)
                .orElseThrow(() -> new RuntimeException("Salle non trouvée"));

        return salleMapper.toDTO(salle);
    }

    @Override
    public SalleDTO update(String codeSalle, SalleDTO salleDTO) {
        Salle salle = salleRepository.findById(codeSalle)
                .orElseThrow(() -> new RuntimeException("Salle introuvé"));

        salle.setContenance(salleDTO.getContenace());
        salle.setDescSalle(salleDTO.getDescSalle());
        salle.setStatutSalle(salleDTO.getStatutSalle());

        salleRepository.save(salle);
        return salleMapper.toDTO(salle);
    }

    @Override
    public void delete(String codeSalle) {
        log.info("recherche de la salle avec code:"+" "+codeSalle);
        boolean exist = salleRepository.existsById(codeSalle);
        if(!exist){
            log.error("salle introuvable modification impossible");
            throw new RuntimeException("Salle inexistante");
        }else{
            salleRepository.deleteById(codeSalle);
            log.info("salle supprimé avec succès");
        }

    }

    @Override
    public SalleDTO findSallesOccupe(SalleStatus salleStatus) {
        return null;
    }
}
