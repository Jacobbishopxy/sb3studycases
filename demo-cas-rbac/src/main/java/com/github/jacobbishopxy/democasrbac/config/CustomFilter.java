package com.github.jacobbishopxy.democasrbac.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class CustomFilter extends GenericFilterBean {

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    Cookie[] cookies = req.getCookies();

    for (Cookie cookie : cookies) {
      System.out.println(cookie.getName() + ": " + cookie.getValue());
      res.addCookie(cookie);
    }

    req.getHeaderNames().asIterator().forEachRemaining(System.out::println);

    System.out.println(">>>>> " + req.getHeader("cookie"));

    chain.doFilter(request, res);
  }
}
