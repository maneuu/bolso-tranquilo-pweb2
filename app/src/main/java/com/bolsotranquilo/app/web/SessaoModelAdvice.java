package com.bolsotranquilo.app.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bolsotranquilo.app.security.SessaoCorrentista;

@ControllerAdvice
public class SessaoModelAdvice {

    private final SessaoCorrentista sessao;

    public SessaoModelAdvice(SessaoCorrentista sessao) {
        this.sessao = sessao;
    }

    @ModelAttribute("sessaoLogada")
    public boolean logado() {
        return sessao.estaLogado();
    }

    @ModelAttribute("sessaoAdmin")
    public boolean admin() {
        return sessao.isAdministrador();
    }

    @ModelAttribute("sessaoNome")
    public String nome() {
        return sessao.getNome();
    }

    @ModelAttribute("sessaoCorrentistaId")
    public Long correntistaId() {
        return sessao.getCorrentistaId();
    }
}
