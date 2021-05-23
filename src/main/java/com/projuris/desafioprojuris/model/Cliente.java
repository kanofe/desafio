package com.projuris.desafioprojuris.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(nullable = false)
    String nome;
    @Column(nullable = false)
    String endereco;
    @Column(nullable = false)
    String fone;
    @Column(nullable = false)
    String email;



}
