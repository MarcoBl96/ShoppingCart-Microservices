package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails {
	private boolean active = true;
	@OneToMany(targetEntity=Role.class, mappedBy="authority", fetch=FetchType.EAGER)
	private final Set<GrantedAuthority> roles = new HashSet<>();

	@Builder
	public User(){
	}

	@Builder
	public User(String username, String password){
		this.emailId = username;
		this.password = password;
		roles.add(new SimpleGrantedAuthority("USER"));
	}

	//@ElementCollection(targetClass=GrantedAuthority.class, fetch = FetchType.EAGER)
	//@JsonDeserialize(as = GrantedAuthority.class)
	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String emailId;
	private String password;
	private boolean enabled;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String getUsername() {
		return this.emailId;
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
		return enabled;
	}
}
