package com.suivi_academique.dto;


import com.suivi_academique.utils.SalleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalleDTO {

    private String codeSalle;

    private String descSalle;

    private int contenace;

    private SalleStatus statutSalle;
}
