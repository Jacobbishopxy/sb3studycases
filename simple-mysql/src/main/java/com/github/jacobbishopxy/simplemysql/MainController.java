package com.github.jacobbishopxy.simplemysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 该类为 Controller 类
@RequestMapping(path = "/demo") // 用于绑定请求的 URL
public class MainController {
  @Autowired // 获取由 Spring 生成的 userRepository bean，使用其处理数据
  private UserRepository userRepository;

  @PostMapping(path = "/add") // 仅处理 POST 请求
  // `@ResponseBody` 用于将返回值转换为 JSON 字符串
  // `@RequestParam` 用于从 GET 或者 POST 请求中获取参数
  public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // 返回 JSON 或者 XML 至用户
    return userRepository.findAll();
  }

}
