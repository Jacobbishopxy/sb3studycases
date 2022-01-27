/**
 * `@SpringBootApplication` 是一个便利的注解，它添加了以下所有：
 *
 * - `@Configuration`：将类标记为 app 上下文的 bean 定义的源。
 * - `@EnableAutoConfiguration`：根据 classpath 设置、其他 beans 以及其它正确的设置，
 * 			通知 Spring Boot 开始添加 beans。例如，如果 `spring-webmvc` 在 classpath 上，
 * 			那么该注解标记 app 为一个 web 应用并且激活关键行为，例如设置一个 `DispatcherServlet`
 * - `@ComponentScan`：告知 Spring 搜索其它组件，设置以及位于 `com/example` 包中的服务，
 * 			使其寻找 controllers。
 */

package com.github.jacobbishopxy.sb3studycases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sb3StudyCasesApplication {

	// main 方法使用了 Spring Boot 的 `SpringApplication.run()` 方法
	// 不再需要 `web.xml` 等其它 xml 文件。该 app 是 100% 的纯 Java，
	// 并且不再需要设置任何 plumbing 或是 infrastructure。
	public static void main(String[] args) {
		SpringApplication.run(Sb3StudyCasesApplication.class, args);
	}

}
