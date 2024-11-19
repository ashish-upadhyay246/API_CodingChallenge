package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Entity.Users;
import com.example.Repository.UserRepo;


@Service
public class UserService {
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	JWTService service;
	
	@Autowired
	AuthenticationManager authManager;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public Users register(Users user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
		
	}

	public String verify(Users u) {
	    // Fetch the user from the database based on the username
	    Users userFromDb = repo.findByUsername(u.getUsername());
	    
	    if (userFromDb != null && encoder.matches(u.getPassword(), userFromDb.getPassword())) {
	        // If password matches, authenticate the user and generate token
	        Authentication authentication = authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword())
	        );
	        
	        if (authentication.isAuthenticated()) {
	            return service.generateToken(u.getUsername()); // Return generated token
	        }
	    }
	    
	    // If authentication fails
	    return "Authentication failed";
	}

}
