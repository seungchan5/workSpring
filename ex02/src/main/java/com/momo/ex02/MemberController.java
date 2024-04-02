package com.momo.ex02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	@GetMapping()
	public String login() {
		return "login";
	}
}
