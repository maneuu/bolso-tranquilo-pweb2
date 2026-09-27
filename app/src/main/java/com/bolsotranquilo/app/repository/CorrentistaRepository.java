package com.bolsotranquilo.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Correntista;

public interface CorrentistaRepository extends JpaRepository<Correntista, Long> {

    Optional<Correntista> findByLogin(String login);
}
