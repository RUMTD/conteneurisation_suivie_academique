package com.suivi_academique.entities;

import com.suivi_academique.utils.RolePersonnel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Personnel")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personnel implements UserDetails {

    @Id
    @Column(unique = true)
    private String codePersonnel;

    @Basic(optional = false)
    private String nomPersonnel;

    @Basic(optional = false)
    private String loginPersonnel;

    @Basic(optional = false)
    private String padPersonnel;

    @Basic(optional = false)
    private String sexePersonnel;

    @Basic(optional = false)
    private String phonePersonnel;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private RolePersonnel rolePersonnel;

    @OneToMany(mappedBy = "personnelProg", cascade = CascadeType.ALL)
    private List<Programmation> programmations;

    @OneToMany(mappedBy = "personnelVal", cascade = CascadeType.ALL)
    private List<Programmation> validations;

    //Implementation de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + rolePersonnel.name()));

    }

    @Override
    public String getPassword(){

        return this.padPersonnel;

    }

    @Override
    public String getUsername() {
        return this.loginPersonnel;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
