package com.labym.flood.admin.repository;

import com.labym.flood.admin.model.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {
    boolean existsByNameOrCode(String name,String code);
}
