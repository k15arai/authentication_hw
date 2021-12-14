package com.kenjiarai.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kenjiarai.authentication.models.User;
import com.kenjiarai.authentication.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// register user and hash their password // could also be incorporated in create method
	public User registerUser(User user) {
		
		// Check to see if email has already been registered
		if ( this.userRepository.findByEmail(user.getEmail()) != null ) return null;
		
		// Hash password
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		
		// set up the first account as an Admin
		if ( this.userRepository.getCount() == 0 ) user.setIsAdmin(true); 
		
		// Save user
		return userRepository.save(user);
	}
	
	// authenticate user
	public boolean authenticateUser(String email, String password) {
		// 1) check to see if email exists 
		User foundUser = userRepository.findByEmail(email);
		
		// 2) if we cannot find it by email, return false
		if (foundUser == null) {
			return false;
		} else {
			// if it can be found, check the password. order matters!! 
			// if passwords match, return true, else, return false
			// sometimes you will return the user, here we return boolean
			if(BCrypt.checkpw(password, foundUser.getPassword())) {
				return true;		
			} else {
				return false;
			}
		}
	}
	
	// find user by email
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	// find user by id
	public User findUserById(Long id) {
		Optional<User> u = userRepository.findById(id);
		
		if(u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}	
}
