package com.projuris.desafioprojuris.repository;

import com.projuris.desafioprojuris.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
