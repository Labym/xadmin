package com.labym.flood.admin.service;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.model.entity.Role;
import com.labym.flood.admin.model.vm.MenuTree;
import com.labym.flood.admin.model.vm.MenuVM;
import com.labym.flood.admin.repository.ResourceRepository;
import com.labym.flood.admin.repository.RoleRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.SecurityUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.labym.flood.admin.constant.StatusCode.ACCESS_DENIED;


@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    public void create(Role role){

    }
}
