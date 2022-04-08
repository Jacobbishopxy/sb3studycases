package com.github.jacobbishopxy.democasrbac.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping("/")
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!\n" + auth.getPrincipal();
  }

  @GetMapping("/now")
  public String now() {
    return "now: " + LocalDateTime.now();
  }
}
