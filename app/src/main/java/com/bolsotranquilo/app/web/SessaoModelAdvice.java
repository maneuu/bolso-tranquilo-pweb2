package com.bolsotranquilo.app.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bolsotranquilo.app.model.enums.Papel;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class SessaoModelAdvice {

    @ModelAttribute("sessaoLogada")
    public boolean logado(HttpSession session) {
        return session.getAttribute("correntistaId") != null;
    }

    @ModelAttribute("sessaoAdmin")
    public boolean admin(HttpSession session) {
        return Papel.ADMINISTRADOR.equals(session.getAttribute("papel"));
    }

    @ModelAttribute("sessaoNome")
    public String nome(HttpSession session) {
        return (String) session.getAttribute("sessaoNome");
    }

    @ModelAttribute("sessaoCorrentistaId")
    public Long correntistaId(HttpSession session) {
        return (Long) session.getAttribute("correntistaId");
    }
}
