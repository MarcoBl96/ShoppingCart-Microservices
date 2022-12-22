package com.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role")

public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private final String authority;

    public Role(String authority){
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
