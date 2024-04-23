package com.s11.exercicio_semanal.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nota")
@Data
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_id")
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "caderno_id", nullable = false)
    private CadernoEntity caderno;
}

