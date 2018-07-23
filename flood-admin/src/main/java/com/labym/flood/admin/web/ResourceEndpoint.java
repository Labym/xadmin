package com.labym.flood.admin.web;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.model.entity.Resource_;
import com.labym.flood.admin.repository.ResourceRepository;
import com.labym.flood.admin.service.ResourceService;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.labym.flood.admin.constant.PermissionEnum;

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
    public void createResource(@RequestBody Resource resource) {
        resourceService.create(resource);
    }

    @PreAuthorize("hasPermission()")
    @GetMapping
    public ResponseEntity list(@RequestParam(required = false) String name,
                    @RequestParam(required = false) ResourceType type, Pageable pageable) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setType(type);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(Resource_.type.getName(), ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
                .withMatcher(Resource_.name.getName(), ExampleMatcher.GenericPropertyMatchers.contains());

     return ResponseEntity.ok(  resourceRepository.findAll( Example.of(resource, matcher),pageable));
    }
}
