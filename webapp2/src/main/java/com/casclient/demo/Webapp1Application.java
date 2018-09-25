package com.casclient.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cache.annotation.EnableCaching;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.casclient.demo.dao")
@EnableCaching
@RestController
public class Webapp1Application {

	public static void main(String[] args) {
		SpringApplication.run(Webapp1Application.class, args);
	}

	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable String name) {
		return "Hello " + name + ", have a good day!!";
	}

}
