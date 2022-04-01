package com.sample.lms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {
	
	@GetMapping("/admin/main")
	public String main() {
		return "";
	}
	
}
