package com.github.jacobbishopxy.simplesecurity.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JWTUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response,
      javax.servlet.FilterChain filterChain)
      throws IOException, ServletException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
      String jwt = authHeader.substring(7);
      if (jwt == null || jwt.isBlank()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
      } else {
        try {
          String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
          UserDetails userDetails = userDetailsService.loadUserByUsername(email);
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              email,
              userDetails.getPassword(),
              userDetails.getAuthorities());
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        } catch (JWTVerificationException exc) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }
      }
    }

    filterChain.doFilter(request, response);
  }

}
