package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.AuthRequest;
import com.suivi_academique.dto.AuthResponse;
import com.suivi_academique.dto.PersonnelDTO;

public interface AuthentificationInterface {
    public AuthResponse authenticate(PersonnelDTO request);

    public AuthResponse register(PersonnelDTO personnel);
}
