package com.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.content.dao.UserDao;
import com.content.model.User;

@Controller
public class UserController {
	@Autowired
	UserDao userdao;

	@GetMapping("/register")
	public String register(Model model) {
		User user =new User();
		model.addAttribute("user",user);
		return "Login";
	}
    @PostMapping("/registeruser")
	public String registerUser(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("Cpassword")String Cpassword,
			@RequestParam("email")String email, RedirectAttributes redirAttrs) {
    	User user=new User();
    	user.setUserName(username);
    	user.setEmail(email);
    	user.setConfirmPassword(Cpassword);
    	user.setPassword(password);
    	userdao.save(user);
    	redirAttrs.addFlashAttribute("success", "Signup Successfully..");
		return "redirect:/register";
	}
	
	@PostMapping("/loginuser")
	public String loginUser(@RequestParam("userName")String username,@RequestParam("password")String password
			,RedirectAttributes redir) {
		
	
	User user=userdao.findByUserName(username);

		if(user!=null && password.equals(user.getPassword()) ) {
		return "index";
		}
		else {
			redir.addFlashAttribute("Perror","Invalid Username or password.");
			return "redirect:/register";
		}
		
		
		
		
		
	}
}
