package com.suivi_academique.config;

import com.suivi_academique.entities.Personnel;
import com.suivi_academique.repositories.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.suivi_academique.security.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {
    private final PersonnelRepository personnelRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> personnelRepository.findByLoginPersonnel(username)
                .map(this::mapToCustomUserDetails)  // ← Mapper vers CustomUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("Personnel non trouvé: " + username));
    }

    private CustomUserDetails mapToCustomUserDetails(Personnel personnel) {
        return new CustomUserDetails(
                personnel.getCodePersonnel(),  // ← ID
                personnel.getLoginPersonnel(),
                personnel.getPadPersonnel(),
                List.of(new SimpleGrantedAuthority("ROLE_" + personnel.getRolePersonnel()))
        );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        // DEBUG -> vérifier la comparaison de mot de passe
        authProvider.setPreAuthenticationChecks(user -> {
            System.out.println("Password userDetails : " + user.getPassword());
        });

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
