package com.bolsotranquilo.app.model;

import java.util.ArrayList;
import java.util.List;

import com.bolsotranquilo.app.model.enums.TipoConta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "correntista_id", nullable = false)
    private Correntista correntista;

    private String numero;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    @Column(name = "dia_fechamento")
    private Integer diaFechamento;

    @OneToMany(mappedBy = "conta")
    private List<Transacao> transacoes = new ArrayList<>();
}
