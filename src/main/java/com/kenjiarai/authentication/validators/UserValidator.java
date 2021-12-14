package com.kenjiarai.authentication.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator; // be careful not to import "import javax.validation.Validator;"

import com.kenjiarai.authentication.models.User;
import com.kenjiarai.authentication.repositories.UserRepository;

@Component // Spring Boot will go through and collect so make sure this is here
public class UserValidator implements Validator {
	
	@Autowired
	private UserRepository userRepository;
	
	// This is required to connect to the specific Model
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	// This section will add to the other validations that exist in the model. Will show up in form.
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		// the first parameter is the name of the attribute in the model 
		// the second parameter is a 'slug' - need one more file for the custom messages in src/main/resources
		// the file will be a "messages.properties" file
		if( this.userRepository.findByEmail(user.getEmail()) != null ) {
			errors.rejectValue("email", "Unique");
		}
		
		if ( !user.getPasswordConfirmation().equals(user.getPassword()) ) {
			errors.rejectValue("passwordConfirmation", "Match");
		}
	}

}
