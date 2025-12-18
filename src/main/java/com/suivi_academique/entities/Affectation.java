package com.suivi_academique.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Affectation implements Serializable {

    @EmbeddedId
    private AffectationId codeAffectation;

    @MapsId("codePersonnel") // lie à l'attribut codePersonnel dans AffectationId
    @ManyToOne
    @JoinColumn(name = "code_personnel", referencedColumnName = "codePersonnel")
    private Personnel personnel;

    @MapsId("codeCours") // lie à l'attribut codeCours dans AffectationId
    @ManyToOne
    @JoinColumn(name = "code_cours", referencedColumnName = "codeCours")

    private Cours cours;

    public Affectation() {

    }
}
