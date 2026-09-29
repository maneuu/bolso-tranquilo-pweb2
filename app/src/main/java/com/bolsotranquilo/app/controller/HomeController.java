package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsotranquilo.app.model.enums.Papel;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        if (!Papel.ADMINISTRADOR.equals(session.getAttribute("papel"))) {
            return "inicio-correntista";
        }
        return "index";
    }
}