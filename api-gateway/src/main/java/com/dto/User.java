package com.dto;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//Needed
//(no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private String username;
    private String password;

    private boolean active = true;
    private Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

    @Builder
    public User(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println("USER erstellt: " + user.getAuthorities().toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
