package com.projuris.desafioprojuris.repository;

import com.projuris.desafioprojuris.model.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdermRepository extends JpaRepository<Ordem, Long> {

    @Query("SELECT t FROM Ordem t WHERE t.paussed = true")
    public List<Ordem> findPaused();

}
