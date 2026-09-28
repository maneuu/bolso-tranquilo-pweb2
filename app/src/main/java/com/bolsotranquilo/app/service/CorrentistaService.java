package com.bolsotranquilo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Correntista;
import com.bolsotranquilo.app.repository.CorrentistaRepository;

@Service
public class CorrentistaService {

    private final CorrentistaRepository correntistaRepository;

    public CorrentistaService(CorrentistaRepository correntistaRepository) {
        this.correntistaRepository = correntistaRepository;
    }

    public Correntista cadastrar(Correntista correntista) {
        correntistaRepository.findByLoginIgnoreCase(correntista.getLogin()).ifPresent(existente -> {
            throw new IllegalArgumentException("Já existe um correntista com esse login.");
        });
        return correntistaRepository.save(correntista);
    }

    public List<Correntista> listarTodos() {
        return correntistaRepository.findAll();
    }

    public Correntista buscarPorId(Long id) {
        return correntistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Correntista não encontrado: " + id));
    }
}
