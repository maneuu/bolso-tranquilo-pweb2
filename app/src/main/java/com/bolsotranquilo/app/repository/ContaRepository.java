package com.bolsotranquilo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsotranquilo.app.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByCorrentistaId(Long correntistaId);
}
