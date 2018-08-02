package com.demo.aouth.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.common.exception.UserException;

@Controller
public class LoginController {

	//it is default page for mvc
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String hello(final Model model) {

		model.addAttribute("name", "John Doe");
		

		return "login";
	}
	
	//invoking this url from login page will redirect to facbook to allow the access resource from user and after granting the permission facebook will redirect to "http://localhost:8080/users/user/facebook" which path of our application  end point
	@GetMapping("/login/facebook")
	public RedirectView  getRedirectUrl() throws UserException {		
		String redirctUrl="https://www.facebook.com/dialog/oauth?client_id=233668646673605&redirect_uri=http://localhost:8080/users/user/facebook&response_type=code&state=lHYg24";
		return new RedirectView(redirctUrl);
	}
}