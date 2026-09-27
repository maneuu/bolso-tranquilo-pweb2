package com.bolsotranquilo.app.service;

import org.springframework.stereotype.Service;

import com.bolsotranquilo.app.model.Comentario;
import com.bolsotranquilo.app.model.Transacao;
import com.bolsotranquilo.app.repository.ComentarioRepository;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TransacaoService transacaoService;

    public ComentarioService(ComentarioRepository comentarioRepository, TransacaoService transacaoService) {
        this.comentarioRepository = comentarioRepository;
        this.transacaoService = transacaoService;
    }

    public Comentario adicionar(Long transacaoId, String texto) {
        if (comentarioRepository.existsByTransacaoId(transacaoId)) {
            throw new IllegalArgumentException("Essa transação já possui um comentário.");
        }
        Transacao transacao = transacaoService.buscarPorId(transacaoId);

        Comentario comentario = new Comentario();
        comentario.setTransacao(transacao);
        comentario.setTexto(texto);
        return comentarioRepository.save(comentario);
    }

    public Comentario editar(Long comentarioId, String novoTexto) {
        Comentario comentario = buscarPorId(comentarioId);
        comentario.setTexto(novoTexto);
        return comentarioRepository.save(comentario);
    }

    public void excluir(Long comentarioId) {
        comentarioRepository.deleteById(comentarioId);
    }

    public Comentario buscarPorId(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado: " + id));
    }

    public Comentario buscarPorTransacao(Long transacaoId) {
        return comentarioRepository.findByTransacaoId(transacaoId).orElse(null);
    }
}
