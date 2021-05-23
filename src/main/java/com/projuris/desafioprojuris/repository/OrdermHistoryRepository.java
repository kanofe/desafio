package com.projuris.desafioprojuris.repository;

import com.projuris.desafioprojuris.model.Ordem;
import com.projuris.desafioprojuris.model.OrdemHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdermHistoryRepository extends JpaRepository<OrdemHistory, Long> {

    @Query("SELECT t FROM OrdemHistory t WHERE t.ordem = :order")
    public List<OrdemHistory> find(@Param("order")Ordem order);

}
