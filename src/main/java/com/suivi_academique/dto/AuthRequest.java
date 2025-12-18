package com.suivi_academique.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull(message = "Le login ne peut pas être null")
    @NotBlank(message = "Le login ne peut pas être vide")
    private String login;

    @NotNull(message = "Le mot de passe ne peut pas être null")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
    // ... getters et setters
}
