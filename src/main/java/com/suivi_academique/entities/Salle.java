package com.suivi_academique.entities;

import com.suivi_academique.utils.SalleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Salle")
@AllArgsConstructor
@NoArgsConstructor
public class Salle {

    @Id
    @Basic(optional = false)
    private String codeSalle ;
    private String descSalle ;
    @Basic(optional = false)
    private int contenance;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private SalleStatus statutSalle;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL)
    private List<Programmation> programmations;

}
