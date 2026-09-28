package com.bolsotranquilo.app.security;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.bolsotranquilo.app.model.enums.Papel;

/**
 * Guarda quem está logado, um objeto por sessão HTTP (@SessionScope).
 * Faz o papel que, com Spring Security, seria coberto pelo
 * Authentication/UserDetails — aqui é só um "quem sou eu" simples.
 */
@Component
@SessionScope
public class SessaoCorrentista implements Serializable {

    private Long correntistaId;
    private String nome;
    private Papel papel;

    public void logar(Long correntistaId, String nome, Papel papel) {
        this.correntistaId = correntistaId;
        this.nome = nome;
        this.papel = papel;
    }

    public void deslogar() {
        this.correntistaId = null;
        this.nome = null;
        this.papel = null;
    }

    public boolean estaLogado() {
        return correntistaId != null;
    }

    public boolean isAdministrador() {
        return papel == Papel.ADMINISTRADOR;
    }

    public Long getCorrentistaId() {
        return correntistaId;
    }

    public String getNome() {
        return nome;
    }

    public Papel getPapel() {
        return papel;
    }
}
