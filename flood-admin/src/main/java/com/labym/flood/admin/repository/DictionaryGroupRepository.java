package com.labym.flood.admin.repository;

import com.labym.flood.admin.model.entity.DictionaryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryGroupRepository extends JpaRepository<DictionaryGroup,Long> {
    boolean existsByNameOrCode(String name,String code);
}
