package com.ejemplo.servicioexcel.controller;

import com.ejemplo.servicioexcel.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Validar credenciales. Aquí asume que son válidas.
        // En una aplicación real, valida contra una base de datos o servicio de autenticación.

        String token = jwtUtil.generateToken(username);
        return "Bearer " + token;
    }
}
