package com.projuris.desafioprojuris.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(nullable = false)
    String tipoItem;
    @Column(nullable = false)
    String marca;
    @Column(nullable = false)
    String problem;

}
