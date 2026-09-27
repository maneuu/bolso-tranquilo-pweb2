package com.bolsotranquilo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaIdOrderByDataDesc(Long contaId);
}
