package com.bolsotranquilo.app.model;

import java.util.ArrayList;
import java.util.List;

import com.bolsotranquilo.app.model.enums.Papel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "correntista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Correntista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    private String nome;

    private String login;

    private String senha;

    private boolean bloqueado;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", length = 20)
    private Papel papel;

    @OneToMany(mappedBy = "correntista")
    private List<Conta> contas = new ArrayList<>();
}