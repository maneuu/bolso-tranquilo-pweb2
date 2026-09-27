package com.bolsotranquilo.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Categoria;
import com.bolsotranquilo.app.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarAtivas() {
        return categoriaRepository.findByAtivaTrueOrderByNaturezaAscOrdemAsc();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
    }
}
