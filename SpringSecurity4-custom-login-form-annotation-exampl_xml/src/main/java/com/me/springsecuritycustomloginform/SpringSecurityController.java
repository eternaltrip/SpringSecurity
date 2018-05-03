package com.me.springsecuritycustomloginform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SpringSecurityController {
	  @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)  
	    public String homePage(ModelMap model) {  
	        model.addAttribute("greeting", "Hi, Welcome to mysite. ");  
	        return "welcome";  
	    }  
	   
	    @RequestMapping(value = "/admin", method = RequestMethod.GET)  
	    public String adminPage(ModelMap model) {  
	        model.addAttribute("user", getPrincipal());  
	        return "admin";  
	    }  
	   
	    @RequestMapping(value = "/db", method = RequestMethod.GET)  
	    public String dbaPage(ModelMap model) {  
	        model.addAttribute("user", getPrincipal());  
	        return "dba";  
	    }  
	   
	   
	    
	    @RequestMapping(value = "/login", method = RequestMethod.GET)  
	    public String loginPage() {  
	        return "login";  
	    }  
	    
	    
	    
	      /**这里 首先我们在使用SecurityContextHolder.getContext().getAuthentication() 之前校验该用户是否已经被验证过。
			然后调用SecurityContextLogoutHandler().logout(request, response, auth)  来退出
			
			logout 调用流程：
			
			 1 将 HTTP Session 作废，解绑其绑定的所有对象。
			
			 2 从SecurityContext移除Authentication 防止并发请求的问题。
			
			 3 显式地清楚当前线程的上下文里的值。
			
			在应用的其他地方不再需要处理 退出。
			**/
	    @RequestMapping(value="/logout", method = RequestMethod.GET)  
	    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {  
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
	        if (auth != null){      
	            new SecurityContextLogoutHandler().logout(request, response, auth);  
	        }  
	        return "redirect:/login?logout";  //这里不明白 为什么要这样写一个？号
	    } 
	    
	   
	    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)  
	    public String accessDeniedPage(ModelMap model) {  
	        model.addAttribute("user", getPrincipal());  
	        return "accessDenied";  
	    }  
	       
	    /**
	     * 这个方法是从登录成功的信息里获取用户信息
	     * @return
	     */
	    private String getPrincipal(){  
	        String userName = null;  
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
	   
	        if (principal instanceof UserDetails) {  
	            userName = ((UserDetails)principal).getUsername();  
	        } else {  
	            userName = principal.toString();  
	        }  
	        return userName;  
	    }  
}
