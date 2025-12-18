package com.suivi_academique.dto;

import com.suivi_academique.entities.Personnel;
import com.suivi_academique.utils.RolePersonnel;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelDTO {

    private String codePersonnel;

    private String nomPersonnel;

    private String loginPersonnel;

    private String padPersonnel;

    private String sexePersonnel;

    private String phonePersonnel;

    private RolePersonnel rolePersonnel;

}
