package com.suivi_academique.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursDTO {

    @Length(min=5, max=100)
    private String codeCours;

    @Length(min=1, max=100)
    private String labelCours;

    private String descCours;

    private String nbCreditCours;

    private String nbHeureCours;




}
