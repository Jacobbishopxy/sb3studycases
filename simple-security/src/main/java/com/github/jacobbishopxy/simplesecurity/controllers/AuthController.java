package com.github.jacobbishopxy.simplesecurity.controllers;

import java.util.Collections;
import java.util.Map;

import com.github.jacobbishopxy.simplesecurity.entity.User;
import com.github.jacobbishopxy.simplesecurity.models.LoginCredentials;
import com.github.jacobbishopxy.simplesecurity.repository.UserRepo;
import com.github.jacobbishopxy.simplesecurity.security.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public Map<String, Object> registerHandler(@RequestBody User user) {
    String encodedPass = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPass);
    user = userRepo.save(user);
    String token = jwtUtil.generateToken(user.getEmail());
    return Collections.singletonMap("jwt-token", token);
  }

  @PostMapping("/login")
  public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
    try {
      UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
          body.email(),
          body.password());
      authManager.authenticate(authInputToken);
      String token = jwtUtil.generateToken(body.email());

      return Collections.singletonMap("jwt-token", token);
    } catch (AuthenticationException authExc) {
      throw new RuntimeException("Invalid Login Credentials");
    }
  }

}
