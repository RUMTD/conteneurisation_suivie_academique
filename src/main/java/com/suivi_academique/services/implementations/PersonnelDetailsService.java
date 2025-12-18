package com.suivi_academique.services.implementations;


import com.suivi_academique.repositories.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelDetailsService implements UserDetailsService {
    private final PersonnelRepository personnelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return personnelRepository.findByLoginPersonnel(username).orElseThrow(() -> new UsernameNotFoundException("Personnel introuvable avec le login: " + username));
    }

}
