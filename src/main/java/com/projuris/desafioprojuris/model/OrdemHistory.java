package com.projuris.desafioprojuris.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedQuery(name = "OrdemHistory.findByOrdemId", query = "SELECT p FROM OrdemHistory p WHERE p.ordem.id = ?1 ")
public class OrdemHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    long id;

    @JoinColumn(name = "ordem_id", referencedColumnName = "id")
    @ManyToOne(optional=false)
    @JsonManagedReference
    Ordem ordem;

    @Column(nullable = false)
    String motion;

}
