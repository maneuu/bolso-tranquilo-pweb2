package com.bolsotranquilo.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaIdOrderByDataDesc(Long contaId);

    List<Transacao> findByContaIdAndDataGreaterThanEqualOrderByDataDesc(Long contaId, LocalDate dataInicio);

    List<Transacao> findByContaIdAndDataLessThanEqualOrderByDataDesc(Long contaId, LocalDate dataFim);

    List<Transacao> findByContaIdAndDataBetweenOrderByDataDesc(Long contaId,
                                                                LocalDate dataInicio,
                                                                LocalDate dataFim);
}
