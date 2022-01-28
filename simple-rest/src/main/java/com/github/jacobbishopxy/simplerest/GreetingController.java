/**
 * 资源的 controller
 *
 * `@GetMapping` 注解确保 HTTP GET 请求 `/greeting` 被映射到 greeting() 方法
 *
 * 类似于 `@PostMapping` 可以用来映射 HTTP POST 请求
 * `@RequestMapping` 可以用来映射 HTTP 所有请求，例如 `@RequestMapping(method=GET)`
 *
 * `@RequestParam` 可以用来映射请求参数，例如 `@RequestParam("name")`
 *
 * 与传统的 MVC 不同，RESTFUL 的 web 服务 controller 提前展示了 HTTP 的响应体是如何被创建的。
 * 相较于使用 view 来展示服务端的渲染结果至 HTML，RESTFUL 的 web 服务 controller 填充并返回
 * 一个 Greeting 对象。该对象会被自动序列化为 JSON 并填充到 HTTP 响应体中。
 *
 * 使用 `@RestController` 注解确保返回的对象是一个 RESTful 的对象，而不是一个 view。这是一种
 * 省略包含 `@Controller` 与 `@ResponseBody` 的简写方式。
 *
 * `Greeting` 对象必须转为 JSON。这都是有劳与 Spring 的 HTTP 信息支持才不需要手动转换的。
 * 因为 Jackson 2 在 classpath 上，Spring 的 `MappingJackson2HttpMessageConverter` 就会自动
 * 将 `Greeting` 对象转为 JSON。
 */

package com.github.jacobbishopxy.simplerest;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping(value = "/greeting")
  public Greeting getMethodName(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}
