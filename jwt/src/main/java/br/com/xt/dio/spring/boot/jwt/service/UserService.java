package br.com.xt.dio.spring.boot.jwt.service;

import br.com.xt.dio.spring.boot.jwt.entity.User;
import br.com.xt.dio.spring.boot.jwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void createUser(User user){
        String pass = user.getPassword();
        //criptografando antes de salvar no banco
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }
}
