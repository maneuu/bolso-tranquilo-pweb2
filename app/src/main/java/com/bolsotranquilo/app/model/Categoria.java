package com.bolsotranquilo.app.model;

import java.util.ArrayList;
import java.util.List;

import com.bolsotranquilo.app.model.enums.NaturezaCategoria;

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
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    private String nome;

    private boolean ativa;

    @Enumerated(EnumType.STRING)
    private NaturezaCategoria natureza;

    private int ordem;

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes = new ArrayList<>();
}
