package com.suivi_academique.controllers;


import com.suivi_academique.dto.AuthRequest;
import com.suivi_academique.dto.AuthResponse;
import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.services.implementations.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthentificationService authenticationService;

    @PostMapping("/login")
    public AuthResponse authenticate(@RequestBody  PersonnelDTO request) {
        System.out.println("Login reçu : " + request.getLoginPersonnel());
        System.out.println("Password reçu : " + request.getPadPersonnel());
        try {
            return authenticationService.authenticate(request);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody PersonnelDTO personnel
    ) {
        return ResponseEntity.ok(authenticationService.register(personnel));
    }
}
