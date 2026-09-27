package com.bolsotranquilo.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    Optional<Comentario> findByTransacaoId(Long transacaoId);

    boolean existsByTransacaoId(Long transacaoId);
}
