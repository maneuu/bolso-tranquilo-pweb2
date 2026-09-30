package com.bolsotranquilo.app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Categoria;
import com.bolsotranquilo.app.model.Conta;
import com.bolsotranquilo.app.model.Transacao;
import com.bolsotranquilo.app.model.enums.Movimento;
import com.bolsotranquilo.app.repository.TransacaoRepository;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final CategoriaService categoriaService;

    public TransacaoService(TransacaoRepository transacaoRepository,
                             ContaService contaService,
                             CategoriaService categoriaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
        this.categoriaService = categoriaService;
    }

    public Transacao criar(Long contaId, Long categoriaId, Transacao transacao) {
        Conta conta = contaService.buscarPorId(contaId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        return transacaoRepository.save(transacao);
    }

    public Transacao editar(Long id, Long categoriaId, Transacao dadosAtualizados) {
        Transacao transacao = buscarPorId(id);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        transacao.setData(dadosAtualizados.getData());
        transacao.setDescricao(dadosAtualizados.getDescricao());
        transacao.setValor(dadosAtualizados.getValor());
        transacao.setMovimento(dadosAtualizados.getMovimento());
        transacao.setCategoria(categoria);

        return transacaoRepository.save(transacao);
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada: " + id));
    }

    public List<Transacao> listarPorConta(Long contaId) {
        return transacaoRepository.findByContaIdOrderByDataDesc(contaId);
    }

    public List<Transacao> listarPorContaEPeriodo(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null && dataFim == null) {
            return transacaoRepository.findByContaIdOrderByDataDesc(contaId);
        }
        if (dataInicio == null) {
            return transacaoRepository.findByContaIdAndDataLessThanEqualOrderByDataDesc(contaId, dataFim);
        }
        if (dataFim == null) {
            return transacaoRepository.findByContaIdAndDataGreaterThanEqualOrderByDataDesc(contaId, dataInicio);
        }
        return transacaoRepository.findByContaIdAndDataBetweenOrderByDataDesc(contaId, dataInicio, dataFim);
    }

    public BigDecimal calcularSaldo(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(transacao -> transacao.getMovimento() == Movimento.CREDITO
                        ? transacao.getValor()
                        : transacao.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
