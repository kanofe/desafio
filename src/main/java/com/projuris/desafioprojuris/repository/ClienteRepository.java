package com.projuris.desafioprojuris.repository;

import com.projuris.desafioprojuris.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
