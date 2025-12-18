package com.suivi_academique.dto;

import com.suivi_academique.entities.Cours;
import com.suivi_academique.entities.Personnel;
import com.suivi_academique.entities.Salle;
import com.suivi_academique.utils.StatutProgrammation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter

public class ProgrammationDTO {

    private int id;

    private int nbHeure;

    private Date dateProgrammation;

    private Date finProgrammation;

    private StatutProgrammation statutProgrammation;

    private SalleDTO salle;

    private CoursDTO cours;

    private PersonnelDTO personnelProg;

    private PersonnelDTO personnelVal;
}
