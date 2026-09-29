package com.bolsotranquilo.app.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.model.enums.Papel;
import com.bolsotranquilo.app.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String form(HttpSession session) {
        if (session.getAttribute("correntistaId") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam("login") String login,
                              @RequestParam("senha") String senha,
                              HttpSession session,
                              Model model) {
        Optional<Correntista> resultado = loginService.autenticar(login.trim(), senha);

        if (resultado.isEmpty()) {
            model.addAttribute("erro", "Login ou senha inválidos.");
            return "login";
        }

        Correntista correntista = resultado.get();

        if (correntista.isBloqueado()) {
            model.addAttribute("erro", "Seu acesso está bloqueado. Procure o administrador.");
            return "login";
        }

        Papel papel = correntista.getPapel();
        session.setAttribute("correntistaId", correntista.getId());
        session.setAttribute("sessaoNome", correntista.getNome());
        session.setAttribute("papel", papel);

        if (papel == Papel.ADMINISTRADOR) {
            return "redirect:/correntistas";
        }
        return "redirect:/correntistas/" + correntista.getId() + "/contas";
    }

    @PostMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
