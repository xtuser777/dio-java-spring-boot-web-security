package br.com.xt.dio.spring.boot.jwt.controller;

import br.com.xt.dio.spring.boot.jwt.service.UserService;
import br.com.xt.dio.spring.boot.jwt.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public void postUser(@RequestBody User user){
        service.createUser(user);
    }

}
