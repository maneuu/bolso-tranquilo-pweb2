package com.bolsotranquilo.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.repository.CorrentistaRepository;

@Service
public class LoginService {

    private final CorrentistaRepository correntistaRepository;

    public LoginService(CorrentistaRepository correntistaRepository) {
        this.correntistaRepository = correntistaRepository;
    }

    /**
     * Retorna o correntista se login e senha baterem.
     * Não checa bloqueio aqui de propósito — isso é responsabilidade
     * de quem chama, pra poder dar uma mensagem diferente pro usuário
     * (login/senha errado vs. conta bloqueada).
     */
    public Optional<Correntista> autenticar(String login, String senha) {
        return correntistaRepository.findByLogin(login)
                .filter(c -> c.getSenha().equals(senha));
    }
}
