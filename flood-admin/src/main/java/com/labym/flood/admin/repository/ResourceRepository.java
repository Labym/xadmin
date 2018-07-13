package com.labym.flood.admin.repository;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByType(ResourceType type);
}
