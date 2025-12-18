package com.suivi_academique.mappers;


import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.entities.Programmation;
import com.suivi_academique.entities.Salle;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalleMapper {

    public  SalleDTO toDTO(Salle salle){
        return new SalleDTO(
                salle.getCodeSalle(), salle.getDescSalle(), salle.getContenance(), salle.getStatutSalle()
        );
    }

    public Salle toEntity(SalleDTO salleDTO){
        List<Programmation> list = new ArrayList<>();
        return new Salle(
                salleDTO.getCodeSalle(), salleDTO.getDescSalle(), salleDTO.getContenace(), salleDTO.getStatutSalle()
                , list
        );
    }
}
