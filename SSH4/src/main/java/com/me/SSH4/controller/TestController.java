package com.me.SSH4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.SSH4.service.SysUserService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	SysUserService userService;
	
	
	@RequestMapping
	@ResponseBody
	public Object queryInfo() {
		return userService.findBySSO("yangjin");
	}

}
