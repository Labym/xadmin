package com.labym.flood.admin.repository;

import com.labym.flood.admin.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
