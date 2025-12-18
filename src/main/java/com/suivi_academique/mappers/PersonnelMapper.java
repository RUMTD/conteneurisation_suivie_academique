package com.suivi_academique.mappers;


import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.entities.Personnel;
import com.suivi_academique.entities.Programmation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PersonnelMapper {

    public PersonnelDTO toDTO(Personnel personnel){
        return new PersonnelDTO(personnel.getCodePersonnel(), personnel.getNomPersonnel(), personnel.getLoginPersonnel(),
                personnel.getPadPersonnel(), personnel.getSexePersonnel(), personnel.getPhonePersonnel(), personnel.getRolePersonnel()
        );
    }

    public Personnel toEntity(PersonnelDTO personnelDTO){
        List<Programmation> list = new ArrayList<>();
        return new Personnel(personnelDTO.getCodePersonnel(), personnelDTO.getNomPersonnel(), personnelDTO.getLoginPersonnel(),
                personnelDTO.getPadPersonnel(), personnelDTO.getSexePersonnel(), personnelDTO.getPhonePersonnel(), personnelDTO.getRolePersonnel(),
                list, list
        );
    }
}
