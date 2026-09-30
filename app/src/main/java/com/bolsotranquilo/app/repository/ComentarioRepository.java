package com.bolsotranquilo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByTransacaoId(Long transacaoId);

    boolean existsByTransacaoId(Long transacaoId);
}
