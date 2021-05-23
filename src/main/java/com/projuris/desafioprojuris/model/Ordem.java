package com.projuris.desafioprojuris.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@Entity
public class Ordem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @JoinColumn(name = "cli_id", referencedColumnName = "id")
    @ManyToOne(optional=false)
    @JsonManagedReference
    Cliente cliente;

    @JoinColumn(name = "it_id", referencedColumnName = "id")
    @ManyToOne(optional=false)
    @JsonManagedReference
    Item iten;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date dateOrdem;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dateStart;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dateClosed;

    @Column(nullable = true)
    String obsOrdem;

    @Column(nullable = true)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean paussed;


}
