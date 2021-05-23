package com.projuris.desafioprojuris.controller;

import com.projuris.desafioprojuris.dto.OrdemCloseDto;
import com.projuris.desafioprojuris.dto.OrdemCreateDto;
import com.projuris.desafioprojuris.dto.OrdemPauseDto;
import com.projuris.desafioprojuris.dto.OrdemStartDto;
import com.projuris.desafioprojuris.model.Ordem;
import com.projuris.desafioprojuris.model.OrdemHistory;
import com.projuris.desafioprojuris.services.OrdemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("v1/ordens")
public class OrdemController {

    @Autowired
    OrdemService ordemService;


    /**
     * Criação de uma nova Ordem, Retorna  URL para com a Ordem
     * @param ordemDto
     * @param uriComponentsBuilder
     * @return created
     */
    @ApiOperation(
            value="Criação de uma nova Ordem",
            response=ResponseEntity.class,
            notes="Essa operação salva um novo registro com as informações da Ordem.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=201,
                    message="Retorna  URL para com a Ordem",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @PostMapping("create")
    public ResponseEntity<Void> createOrdem(@RequestBody OrdemCreateDto ordemDto, UriComponentsBuilder uriComponentsBuilder){
        Long id = ordemService.createNewOrder(ordemDto);
        UriComponents uriComponents =  uriComponentsBuilder.path("/v1/ordens/{id}").buildAndExpand(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * Incio da Ordem
     * @param startDto
     * @return
     */
    @ApiOperation(
            value="Inicia uma Ordem",
            response=ResponseEntity.class,
            notes="Essa operação da inicio a uma Ordem.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna um ResponseModel da Ordem iniciada",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @PostMapping("startOrdem")
    public ResponseEntity<Ordem> startOrdem(@RequestBody OrdemStartDto startDto){
        return ResponseEntity.ok(ordemService.startOrder(startDto.getId(),startDto));
    }

    /**
     * Fechamento de Ordem
     * @param closeDto
     * @return
     */
    @ApiOperation(
            value="Encerra uma Ordem",
            response=ResponseEntity.class,
            notes="Essa operação da encerra a uma Ordem já iniciada.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna um ResponseModel da Ordem encerrada",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @PostMapping("closeOrdem")
    public ResponseEntity<Ordem> closeOrdem(@RequestBody OrdemCloseDto closeDto){
        return ResponseEntity.ok(ordemService.closeOrdem(closeDto.getId(),closeDto));
    }

    /**
     * Pausa de Ordem
     * @param pauseDto
     * @return
     */
    @ApiOperation(
            value="Pausa uma Ordem",
            response=ResponseEntity.class,
            notes="Essa operação da pausa uma Ordem já iniciada, solicitando o motivo da ordem e gerando um histórico da Pausa.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna um ResponseModel da Ordem Pausada",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @PostMapping("pauseOrdem")
    public ResponseEntity<Ordem> pauseOrdem(@RequestBody OrdemPauseDto pauseDto){
        return ResponseEntity.ok(ordemService.pauseOrdem(pauseDto.getId(),pauseDto));
    }

    /**
     * Retorna o Historico de Pausa de Ordem
     * @param id
     * @return
     */
    @ApiOperation(
            value="Retorna o Histórico de pausas de uma Ordem",
            response=ResponseEntity.class,
            notes="Essa operação retorna todos os históricos de Pausas da Ordem.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna uma Lista de ResponseModel das Pausas da  Ordem",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @GetMapping("ordemhistory/{id}")
    public ResponseEntity<List<OrdemHistory>> ordemHitory(@PathVariable("id") long id){
        return ResponseEntity.ok(ordemService.pausedOrdemHistory(id));
    }

    /**
     * Default URL  - Retorna todas as Ordens
     * @return
     */
    @ApiOperation(
            value="Retorna todas as Ordens",
            response=ResponseEntity.class,
            notes="Essa operação retorna todas as Ordem.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna uma Lista de ResponseModel das Ordens",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @GetMapping
    public ResponseEntity<List<Ordem>> getAllOrdens(){
        return ResponseEntity.ok(ordemService.listAllOrdem());
    }

    /**
     * Retorna um Ordem por ID
     * @param id
     * @return
     */
    @ApiOperation(
            value="Retorna uma Ordem",
            response=ResponseEntity.class,
            notes="Essa operação retorna uma Ordem.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna uma ResponseModel da Ordem",
                    response=ResponseEntity.class
            ),
            @ApiResponse(
                    code=500,
                    message="Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response=ResponseEntity.class
            )

    })
    @GetMapping("/{id}")
    public ResponseEntity<Ordem> getOrdem(@PathVariable("id") long id){
        return ResponseEntity.ok(ordemService.getOrdemId(id));
    }

}
