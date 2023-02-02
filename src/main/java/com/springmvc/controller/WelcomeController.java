package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller		//WelcomeController가 컨트롤러임을 나타냄
public class WelcomeController {
	@RequestMapping(value="/home", method=RequestMethod.GET)		//요청 URL이 /home이면 welcome()메소드에 매핑함.
	public String welcome(Model model) {
		model.addAttribute("greeting", "Welcome to BookMarket");
		model.addAttribute("strapline", "Welcome to Web Shopping Mall!");
		return "welcome";
	}
}
