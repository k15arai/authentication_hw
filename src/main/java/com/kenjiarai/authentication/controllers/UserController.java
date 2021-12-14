package com.kenjiarai.authentication.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kenjiarai.authentication.models.User;
import com.kenjiarai.authentication.services.UserService;
import com.kenjiarai.authentication.validators.UserValidator;

@Controller
public class UserController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserValidator userValidator;
	
	// Go to login page
	@GetMapping("/login")
	public String login() {
		return "loginPage.jsp";
	}
	
	// Go to registration page
	@GetMapping("/registration")
	public String registerForm(@ModelAttribute("user") User user) {
		return "registrationPage.jsp";
	}
	
	// Register User
	@PostMapping("/registration") 
	public String registerUser(
			@Valid @ModelAttribute("user") User user,
			BindingResult result,
			HttpSession session) {
		
        // 1) run the custom validator as it will extend the validations in the model
		 this.userValidator.validate(user, result); // - this will use custom validations
		
		// 2) check for errors - if result has errors, return the registration page (don't worry about validations just now)
		if ( result.hasErrors() ) return "registrationPage.jsp";
		
		// else, save the user in the database, save the user id in session, and redirect them to the /home route
		// 3) register user
		this.userService.registerUser(user);
		session.setAttribute("user", user.getId());
		
        // 4) redirect to home 
		return "redirect:/home";
	}
	
	// Login User
	@PostMapping("/login")
	public String loginUser(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
        
		// if the user is authenticated, save their user id in session
		if ( this.userService.authenticateUser(email, password) ) {
			// if true, find logged in user
			User loggedInUser = this.userService.findByEmail(email);
			// set user id to "user" session attribute
			session.setAttribute("user",  loggedInUser.getId());
			model.addAttribute("user", loggedInUser);
			redirectAttributes.addFlashAttribute("message", String.format("Welcome back %s!", loggedInUser.getEmail()));
			
			return "redirect:/home";
		}
		
        // else, add error messages and return the login page
		redirectAttributes.addFlashAttribute("message", "invalid credentials");
		return "redirect:/login";
	}
	
	// Go to home page
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		// get user from session, save them in the model and return them to the homepage
		User loggedInUser = this.userService.findUserById((Long) session.getAttribute("user"));
		
		model.addAttribute("user", loggedInUser);
		// keep going here
		
		return "homePage.jsp";
	}
	
	// Logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// invalidate session
		// session.invalidate();
		// OR
		// remove attribute user from session and it will be logged out
		session.removeAttribute("user");
		// redirect to login page
		return "redirect:/login";
	}
}