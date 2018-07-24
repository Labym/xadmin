package com.labym.flood.admin.web;

import com.labym.flood.admin.constant.Permissions;
import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.dto.ResourceDTO;
import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.model.entity.Resource_;
import com.labym.flood.admin.model.vm.MenuTree;
import com.labym.flood.admin.model.vm.MenuVM;
import com.labym.flood.admin.repository.ResourceRepository;
import com.labym.flood.admin.service.ResourceService;
import com.labym.flood.security.annotation.Permission;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Permission(Permissions.RESOURCE_MANAGER)
@RestController
@RequestMapping("/api/resources")
public class ResourceEndpoint {

    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;

    public ResourceEndpoint(ResourceService resourceService, ResourceRepository resourceRepository) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
    }

    @GetMapping("/current/menus")
    @SecurityConfiguration
    public ResponseEntity currentUserMenus() {
        return ResponseEntity.ok(resourceService.currentUserMenus());
    }


    @Timed
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Permission(Permissions.RESOURCE_CREATE)
    @PreAuthorize("hasPermission()")
    public void createResource(@RequestBody Resource resource) {
        resourceService.create(resource);
    }


    @Timed
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Permission(Permissions.RESOURCE_EDITOR)
    @PreAuthorize("hasPermission()")
    public void editorResource(@RequestBody ResourceDTO resource) {
        resourceService.editor(resource);
    }


    @GetMapping
    @Permission(Permissions.RESOURCE_MANAGER)
    @PreAuthorize("hasPermission()")
    public ResponseEntity list(@RequestParam(required = false) String name,
                               @RequestParam(required = false) ResourceType type,
                               @RequestParam(required = false) Long parentId,
                               Pageable pageable) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setType(type);
        resource.setParentId(parentId);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(Resource_.type.getName(), ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
                .withMatcher(Resource_.name.getName(), ExampleMatcher.GenericPropertyMatchers.contains());

        return ResponseEntity.ok(resourceRepository.findAll(Example.of(resource, matcher), pageable));
    }


    @GetMapping("/tree")
    @Permission(Permissions.RESOURCE_MANAGER)
    @PreAuthorize("hasPermission()")
    public ResponseEntity list() {
        List<MenuVM> menuVMList = resourceRepository.findAll().stream().map(MenuVM::new).collect(Collectors.toList());
        MenuTree menuTree = MenuTree.buildTree(menuVMList,false);
        return ResponseEntity.ok(menuTree);
    }
}
