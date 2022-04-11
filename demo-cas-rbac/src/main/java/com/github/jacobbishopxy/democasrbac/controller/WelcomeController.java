package com.github.jacobbishopxy.democasrbac.controller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping("/")
  public String index() throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return "Hello, " + auth.getName() + "!";
  }

  @GetMapping("/info")
  public String info() throws JsonProcessingException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(auth.getPrincipal());

    return json;
  }

  @GetMapping("/now")
  public String now() {
    return "now: " + LocalDateTime.now();
  }
}
