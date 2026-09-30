package com.bolsotranquilo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Conta;
import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.model.enums.TipoConta;
import com.bolsotranquilo.app.repository.ContaRepository;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final CorrentistaService correntistaService;

    public ContaService(ContaRepository contaRepository, CorrentistaService correntistaService) {
        this.contaRepository = contaRepository;
        this.correntistaService = correntistaService;
    }

    public Conta cadastrar(Long correntistaId, Conta conta) {
        Correntista correntista = correntistaService.buscarPorId(correntistaId);
        conta.setCorrentista(correntista);
        if (TipoConta.CORRENTE.equals(conta.getTipo())) {
            conta.setDiaFechamento(null);
        }
        return contaRepository.save(conta);
    }

    public List<Conta> listarPorCorrentista(Long correntistaId) {
        return contaRepository.findByCorrentistaId(correntistaId);
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + id));
    }
}
