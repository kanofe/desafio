package com.projuris.desafioprojuris.dto;

import com.projuris.desafioprojuris.model.Cliente;
import com.projuris.desafioprojuris.model.Item;
import lombok.Data;

import java.util.Date;


@Data
public class OrdemCreateDto {

    long idClient;
    long idItem;
    Date dateOrdem;

}
