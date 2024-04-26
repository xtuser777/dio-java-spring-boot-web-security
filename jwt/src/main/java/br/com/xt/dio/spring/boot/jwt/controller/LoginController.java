package br.com.xt.dio.spring.boot.jwt.controller;

import br.com.xt.dio.spring.boot.jwt.dto.Login;
import br.com.xt.dio.spring.boot.jwt.dto.Session;
import br.com.xt.dio.spring.boot.jwt.entity.User;
import br.com.xt.dio.spring.boot.jwt.security.JWTCreator;
import br.com.xt.dio.spring.boot.jwt.security.JWTObject;
import br.com.xt.dio.spring.boot.jwt.security.SecurityConfig;
import br.com.xt.dio.spring.boot.jwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public Session login(@RequestBody Login login){
        User user = repository.findByUsername(login.getUsername());
        if(user!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Session sessao = new Session();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(login.getUsername());
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            String token = JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject);
            sessao.setToken(token);
            return sessao;

        } else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }

}
