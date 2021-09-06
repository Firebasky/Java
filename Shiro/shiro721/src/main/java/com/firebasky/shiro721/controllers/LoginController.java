 package com.firebasky.shiro721.controllers;

 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.authc.AuthenticationException;
 import org.apache.shiro.subject.Subject;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RestController;

 @RestController
 @org.springframework.web.bind.annotation.RequestMapping({"/"})
 public class LoginController
 {
   @org.springframework.web.bind.annotation.PostMapping({"/doLogin"})
   public void doLogin(String username, String password)
   {
     Subject subject = SecurityUtils.getSubject();
     try {
      subject.login(new org.apache.shiro.authc.UsernamePasswordToken(username, password,true));
      //shiro
       System.out.println("success");
     } catch (AuthenticationException e) {
      e.printStackTrace();
      System.out.println("fail!");
     }
   }
   
   @GetMapping({"/admin/hello"})
   public String hello() { return "hello"; }
   
   @GetMapping({"/login"})
   public String login() {
     return "please login!";
   }
 }
