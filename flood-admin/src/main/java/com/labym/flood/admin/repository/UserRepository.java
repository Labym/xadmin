package com.labym.flood.admin.repository;

import com.labym.flood.admin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
