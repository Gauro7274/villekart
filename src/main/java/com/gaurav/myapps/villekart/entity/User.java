package com.gaurav.myapps.villekart.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="user_data")
public class User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_seq_id")
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="user_name",unique = true, nullable = false)
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;
	
	private String dateOfBirth;
	
	private String mobileNumber;
	
	@Column(name="role", nullable = false)
	private String role = "User";
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference  // Manages serialization for the parent (User)
    private List<Address> addresses;

    // One User can have many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference  // Manages serialization for the parent (User)
    private List<Orders> orders;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Set.of(()-> role);
	}

	@Override
	public String getUsername() {	
		// TODO Auto-generated method stub
		return username;
	}
	
	
}
