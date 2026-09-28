package com.bolsotranquilo.app.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.model.enums.Papel;
import com.bolsotranquilo.app.security.SessaoCorrentista;
import com.bolsotranquilo.app.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final SessaoCorrentista sessao;

    public LoginController(LoginService loginService, SessaoCorrentista sessao) {
        this.loginService = loginService;
        this.sessao = sessao;
    }

    @GetMapping("/login")
    public String form() {
        if (sessao.estaLogado()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam("login") String login,
                              @RequestParam("senha") String senha,
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
        sessao.logar(correntista.getId(), correntista.getNome(), papel);

        // UC20/21 (visão geral) são exclusivas do admin; o correntista
        // cai direto no contexto dele (as próprias contas).
        if (papel == Papel.ADMINISTRADOR) {
            return "redirect:/correntistas";
        }
        return "redirect:/correntistas/" + correntista.getId() + "/contas";
    }

    @PostMapping("/logout")
    public String sair() {
        sessao.deslogar();
        return "redirect:/login";
    }
}
