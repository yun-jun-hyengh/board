package com.example.board.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomeError {
	
	@GetMapping("/error")
	public String goErrorPage(HttpServletRequest request) {
		return "error/error";
	}
}
