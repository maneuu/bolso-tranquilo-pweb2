package com.bolsotranquilo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsotranquilo.app.security.SessaoCorrentista;

@Controller
public class HomeController {

    private final SessaoCorrentista sessao;

    public HomeController(SessaoCorrentista sessao) {
        this.sessao = sessao;
    }

    @GetMapping("/")
    public String home() {
        if (!sessao.isAdministrador()) {
            return "inicio-correntista";
        }
        return "index";
    }
}