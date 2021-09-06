package com.firebasky.shiro721.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/"})
public class index
{
@RequestMapping({"/index"})
public String printName(String name)
{
return "Hello!" + name;
}
}