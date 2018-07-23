package com.labym.flood.admin.service;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.dto.ResourceDTO;
import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.model.vm.MenuTree;
import com.labym.flood.admin.model.vm.MenuVM;
import com.labym.flood.admin.repository.ResourceRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.labym.flood.admin.constant.StatusCode.ACCESS_DENIED;


@Service
@Transactional(readOnly = true)
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public MenuTree currentUserMenus() {
        Long userId = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new FloodException(ACCESS_DENIED, "Current user login not found"));
        List<MenuVM> menus = resourceRepository.findByType(ResourceType.MENU).stream().map(MenuVM::new).collect(Collectors.toList());
        return MenuTree.buildTree(menus);
    }

    /**
     * @param resource
     */
    @Transactional
    public void create(Resource resource) {
        SecurityUtils.getCurrentUserLogin().map((user) -> {
            resource.setCreateAt(LocalDateTime.now());
            resource.setCreateBy(user);
            resourceRepository.save(resource);
            return resource;
        }).orElseThrow(() -> new FloodException(ACCESS_DENIED, "Current user login not found"));
    }

    public void editor(ResourceDTO resource){
        resourceRepository.findById(resource.getId()).ifPresent(dbResource->{

            if(!StringUtils.isEmpty(resource.getName())){
                dbResource.setName(resource.getName());
            }

            if(!StringUtils.isEmpty(resource.getCode())){
                dbResource.setType(resource.getType());
            }

            if(null!=resource.getExtensions()){
                dbResource.setExtensions(resource.getExtensions());
            }

            if(null!=resource.getSort()){
                dbResource.setSort(resource.getSort());
            }

            if (!StringUtils.isEmpty(resource.getUrl())) {
                dbResource.setUrl(resource.getUrl());
            }

            if(null!=resource.getParentId()){
                dbResource.setParentId(resource.getParentId());
            }
        });
    }
}
