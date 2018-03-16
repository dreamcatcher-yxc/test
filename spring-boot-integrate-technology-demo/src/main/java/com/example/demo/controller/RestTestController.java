package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rest")
public class RestTestController {

    private final RestTemplate restTemplate;

    public RestTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return String.format("hello, %s !!!", name);
    }

    @PostMapping(value = "/users/{id}")
    public void save(@PathVariable String id, @RequestBody User user, HttpServletResponse response) {
        System.out.println(String.format("user id is %s", id));
        System.out.println(String.format("user information is [%s]", user.toString()));
        response.setHeader("Location", "ok");
    }

    @GetMapping(value = "/headers")
    public void headers(@RequestHeader("auth-key") String authKey, HttpServletResponse response) {
        response.setHeader("auth-result", String.format("%s, is ok", authKey));
    }

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        System.out.println(user);
        return "ok";
    }
}
