package com.tech.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tech.model.User;
import com.tech.service.IUserService;
import com.tech.utility.JavaMail;
import com.tech.utility.Utility;

import net.bytebuddy.utility.RandomString;

@Controller
//@RequestMapping("/product")
public class UserController {

	@Autowired
	private IUserService service;
	
	
	@Autowired
	private JavaMail mail;
	
	@GetMapping("/login")
	private String showLogin() {
		return "Login";
	}
	
	@GetMapping("/home")
	private String hoPage() {
		return "Home";
	}
	
	@GetMapping("/about")
	private String aboutPage() {
		return "about";
	}
	
	@GetMapping("/index")
	private String homePage() {
		return "index";
	}
	
	@GetMapping("/register")
	private String userRegister(Model model) {
		model.addAttribute("user",new User());
		return "Register";
	}
	
	@PostMapping("/saveUser")
	private String userSaved(@ModelAttribute User user,Model model) {
		User use = service.save(user);
		if(use.getId() !=0) {
		 mail.sendEmail(user.getEmail(), "Hello", "Hey guys,Welcome to CFC");
		 System.out.println("email is Sent");
		}
		String str = "User is "+use.getId()+"Saved in Database";
		model.addAttribute("message", str);
		model.addAttribute("user",new User());
		return "Register";
	}
	/*
	@GetMapping("/edit/{id}")
	private String editUser(Model model,@PathVariable Integer id) {
			Optional<User> opt = service.getOneUser(id);
			if(service.isExist(id)) {
					model.addAttribute("user", opt.get());
			}else throw new RuntimeException("User"+id+" is Not Found");
			return "UserEdit";
	}
	
	@PostMapping("/update")
	private String updateUser(@ModelAttribute User user, Model model) {
		service.updateUser(user);
		List<User> list = service.getAllUsers();
		model.addAttribute("lis", list);
		return "UserAll";
	}
	
	@GetMapping("/all")
	private String getAll(Model model) {
		return "UserAll";
	}*/
	
	@GetMapping("/forgot")
	public String showForgotPassword() {
		
		return "forgotpass";
	}
	
	@PostMapping("/forgot")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);
		 try {	
		service.updateResetPasswordToken(token, email);
		String resetPasswordLink = Utility.getSiteUrl(request)+ "/resetpassword?token="+ token;
		mail.sendEmailForgot(email, "Reset Your Password", resetPasswordLink);
		model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        
    } catch (Exception ex) {
        model.addAttribute("error", ex.getMessage());
    } 
		return "forgotpass";
	}
	
	@GetMapping("/resetpassword")
	public String resetForgotPassword(@Param(value="token") String token,Model model) {
		User user =	service.getByResetPasswordToken(token);
		model.addAttribute("token", token);
		if(user==null) {
			model.addAttribute("message","Invalid token");
			return "message";
		}
		return "resetpass";
	}
	
	@PostMapping("/resetpassword")
	public String processResetPassword(HttpServletRequest request, Model model) {
	    String token = request.getParameter("token");
	    String password = request.getParameter("password");
	     
	    User user = service.getByResetPasswordToken(token);
	     
	    if (user == null) {
	    	model.addAttribute("title", "Reset your password");
	        model.addAttribute("message", "Invalid Token");
	        return "message";
	    } else {           
	        service.updatePassword(user, password);         
	        model.addAttribute("message", "You have successfully changed your password.");
	    }     
	    return "resetpass";
	}
}
