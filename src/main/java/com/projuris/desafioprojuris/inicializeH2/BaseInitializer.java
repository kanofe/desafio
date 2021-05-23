package com.projuris.desafioprojuris.inicializeH2;

import com.github.javafaker.Faker;
import com.projuris.desafioprojuris.model.Cliente;
import com.projuris.desafioprojuris.model.Item;
import com.projuris.desafioprojuris.repository.ClienteRepository;
import com.projuris.desafioprojuris.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BaseInitializer implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {

        log.info("Inicializando Dados");

        Faker faker =  new Faker();

        for (int i = 0; i < 10; i++) {
            Cliente c = new Cliente();
            c.setNome(faker.name().firstName());
            c.setEndereco(faker.address().fullAddress());
            c.setFone(faker.phoneNumber().cellPhone());
            c.setEmail(faker.internet().emailAddress());

            clienteRepository.save(c);

            Item it = new Item();
            it.setMarca(faker.company().buzzword());
            it.setTipoItem(faker.company().profession());
            it.setProblem(faker.job().title());

            itemRepository.save(it);

        }

        log.info("Fim da Iniciliazação");


    }
}
