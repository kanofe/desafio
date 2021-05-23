package com.projuris.desafioprojuris.services;

import com.projuris.desafioprojuris.dto.OrdemCloseDto;
import com.projuris.desafioprojuris.dto.OrdemCreateDto;
import com.projuris.desafioprojuris.dto.OrdemPauseDto;
import com.projuris.desafioprojuris.dto.OrdemStartDto;
import com.projuris.desafioprojuris.exception.ObjectNotFoundException;
import com.projuris.desafioprojuris.model.Ordem;
import com.projuris.desafioprojuris.model.OrdemHistory;
import com.projuris.desafioprojuris.repository.ClienteRepository;
import com.projuris.desafioprojuris.repository.ItemRepository;
import com.projuris.desafioprojuris.repository.OrdermHistoryRepository;
import com.projuris.desafioprojuris.repository.OrdermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemService {


    @Autowired
    OrdermRepository repository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrdermHistoryRepository historyRepository;


    /**
     * Criação de umo nova Ordem
     * @param ordemCreateDto
     * @return
     */
    public Long createNewOrder(OrdemCreateDto ordemCreateDto) {
        Ordem ordem = new Ordem();
        ordem.setCliente(clienteRepository.getOne(ordemCreateDto.getIdClient()));
        ordem.setIten(itemRepository.getOne(ordemCreateDto.getIdItem()));
        ordem.setDateOrdem(ordemCreateDto.getDateOrdem());
        ordem.setPaussed(true);

        ordem = repository.save(ordem);

        return ordem.getId();
    }

    public List<Ordem> listAllOrdem(){
        return repository.findAll();
    }

    /**
     * Retorna Ordem por id
     * @param id
     * @return Ordem
     */
    public Ordem getOrdemId(long id){
        Optional<Ordem> requestOrdem = repository.findById(id);
        if(requestOrdem.isEmpty()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' not found",id));

        }
        return requestOrdem.get();
    }

    /**
     * Inicio da Ordem
     * @param id
     * @param startDto
     * @return
     */
    @Transactional
    public Ordem startOrder(long id, OrdemStartDto startDto){
        Optional<Ordem> ordemDb = repository.findById(id);
        if(ordemDb.isEmpty()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' not found",id));

        }
        Ordem ordemStart = ordemDb.get();
        ordemStart.setDateStart(startDto.getDatestart());
        ordemStart.setPaussed(false);

        return ordemStart;
    }

    /**
     * Fechamento de Ordem, Gravando a Data de encerramento da Ordem
     * @param id
     * @param closeDto
     * @return Ordem (Fechada)
     */
    @Transactional
    public Ordem closeOrdem(long id, OrdemCloseDto closeDto) {

        Optional<Ordem> ordemDb = repository.findById(id);
        if(ordemDb.isEmpty()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' not found",id));

        }
        Ordem ordemClose = ordemDb.get();
        ordemClose.setObsOrdem(closeDto.getObsOrdem());
        ordemClose.setDateClosed(new Date());

        return ordemClose;
    }

    /**
     * Pause Orderm
     * @param id
     * @param pauseDto
     * @return
     */
    @Transactional
    public Ordem pauseOrdem(long id, OrdemPauseDto pauseDto) {
        Optional<Ordem> ordemDb = repository.findById(id);
        if(ordemDb.isEmpty()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' not found",id));

        }
        Ordem ordemPause = ordemDb.get();
        if(ordemPause.getDateClosed()!=null){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' is closed ",id));
        }

        OrdemHistory history = new OrdemHistory();
        history.setOrdem(ordemPause);
        history.setMotion(pauseDto.getMotion());
        historyRepository.save(history);
        ordemPause.setPaussed(true);

        return ordemPause;
    }

    public List<OrdemHistory> pausedOrdemHistory(long id) {
        Optional<Ordem> ordemDb = repository.findById(id);
        if(ordemDb.isEmpty()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' not found",id));

        }
        Ordem ordemPause = ordemDb.get();
        if(ordemPause.getDateClosed()!=null){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' is closed ",id));
        }
        if(!ordemPause.isPaussed()){
            throw new ObjectNotFoundException(String.format("Ordem id: '%s' is not paussed ",id));
        }
        List<OrdemHistory> list =historyRepository.find(ordemPause);
        return list;

    }
}
