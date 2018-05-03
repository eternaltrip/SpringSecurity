package com.me.springsecurity.controller;

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


/**
 * 第三步
 * 自定义了很多可请求的url。
 * controller类中的方法比较繁琐.getPrincipal 方法返回从Spring SecurityContext中记录的登录的用户。
 * logoutPage 方法简单调用 SecurityContextLogoutHandler().logout(request, response, auth)方法
 * 来处理退出操作。
 * 它很巧妙而且将你从不容易管理的jsp页面退出逻辑中解放出来。
 * 你也许注意到上面没有出现 /login’，因为Spring Security默认会产生和处理。
 * @author Administrator
 *
 */

@Controller
public class HelloWorldController {
    
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
    
    @RequestMapping(value="/login", method = RequestMethod.GET)  
    public String loginPage () {  
       return "login";  
    }  
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)  
       public String logoutPage (HttpServletRequest request, HttpServletResponse response) {  
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
          if (auth != null){      
             new SecurityContextLogoutHandler().logout(request, response, auth);  
          }  
          return "redirect:/login?logout";  
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
