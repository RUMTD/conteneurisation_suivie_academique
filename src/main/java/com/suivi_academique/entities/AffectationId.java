package com.suivi_academique.entities;


import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
public class AffectationId implements Serializable {

    @Basic(optional = false)
    private String codeCours;

    @Basic(optional = false)
    private String codePersonnel;

    public AffectationId(String codeCours, String codePersonnel) {
        this.codeCours = codeCours;
        this.codePersonnel = codePersonnel;
    }

    public AffectationId() {

    }

}
