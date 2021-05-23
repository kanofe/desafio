package com.projuris.desafioprojuris;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projuris.desafioprojuris.controller.OrdemController;
import com.projuris.desafioprojuris.dto.OrdemCreateDto;
import com.projuris.desafioprojuris.exception.ObjectNotFoundException;
import com.projuris.desafioprojuris.model.Cliente;
import com.projuris.desafioprojuris.model.Item;
import com.projuris.desafioprojuris.model.Ordem;
import com.projuris.desafioprojuris.services.OrdemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdemController.class)
public class OrdemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OrdemService ordemService;



    @Captor
    private ArgumentCaptor<OrdemCreateDto> argumentCaptor;

    @Test
    public void postOrderCreateDb() throws Exception {

        OrdemCreateDto orca = new OrdemCreateDto();
        orca.setIdClient(1);
        orca.setIdItem(1);
        orca.setDateOrdem(new Date());

        when(ordemService.createNewOrder(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform( post("/api/ordens/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orca)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location","http://localhost/api/books/1"));
        assertThat(argumentCaptor.getValue().getIdClient() , is(1) );
        assertThat(argumentCaptor.getValue().getIdItem() , is(1) );
    }

    @Test
    public void getAllOrdem() throws Exception {
       Cliente cli = new Cliente();
       Item it = new Item();
       Date dtod = new Date();
       when(ordemService.listAllOrdem()).thenReturn(List.of(newOrdem(1,cli,it,dtod,null,null,"OBS1"),
               newOrdem(2,cli,it,dtod,null,null,"obs2")));
       this.mockMvc
               .perform(get("vi/ordens"))
               .andExpect(content().contentType("application/json"))
               .andExpect(jsonPath("$",hasSize(2)))
               .andExpect(jsonPath("$[0].id",is(1)))
               .andExpect(jsonPath("$[0].obsrrdem",is("OBS1")));


    }

    @Test
    public void getOrdemIdReturnOrdem() throws Exception {
        Cliente cli = new Cliente();
        Item it = new Item();
        Date dtod = new Date();
        when(ordemService.getOrdemId(1L)).thenReturn(newOrdem(1,cli,it,dtod,null,null,"OBS1"));
        this.mockMvc
                .perform(get("vi/ordens/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.obsrrdem",is("OBS1")));


    }

    @Test
    public void getOrdemNotFoundReturn404() throws Exception {
        Cliente cli = new Cliente();
        Item it = new Item();
        Date dtod = new Date();
        when(ordemService.getOrdemId(43L)).thenThrow(new ObjectNotFoundException("Ordem '43' not found"));
        this.mockMvc
                .perform(get("vi/ordens/43"))
                .andExpect(status().isNotFound());

    }

    private Ordem newOrdem(long id, Cliente cli, Item it, Date dtordem, Date dtstar, Date dtfim, String obs){
        Ordem ordem = new Ordem();
        ordem.setId(id);
        ordem.setCliente(cli);
        ordem.setIten(it);
        ordem.setDateOrdem(dtordem);
        ordem.setDateStart(dtstar);
        ordem.setDateClosed(dtfim);
        ordem.setObsOrdem(obs);
        return ordem;
    }


}
