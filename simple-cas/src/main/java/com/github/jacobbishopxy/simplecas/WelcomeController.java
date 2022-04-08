package com.github.jacobbishopxy.simplecas;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping("/")
  @ResponseBody
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!\n" + auth.getPrincipal();
  }

}
