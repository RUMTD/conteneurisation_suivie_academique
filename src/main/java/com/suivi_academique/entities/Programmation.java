package com.suivi_academique.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.suivi_academique.utils.StatutProgrammation;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Programmation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private int nbHeure;

    @Basic(optional = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH.mm.ss")
    private Date dateProgrammation;

    @Basic(optional = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH.mm.ss")
    private Date finProgrammation;

    @Basic(optional = false)
    private StatutProgrammation statutProgrammation;


    @ManyToOne
    @JoinColumn(name = "code_salle", referencedColumnName = "codeSalle" )
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "code_cours", referencedColumnName = "codeCours" )
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "code_personnel_prog", referencedColumnName = "codePersonnel" )
    private Personnel personnelProg;



    @ManyToOne
    @JoinColumn(name = "code_personnel_val", referencedColumnName = "codePersonnel" )
    private Personnel personnelVal;

    public Programmation() {

    }
}
