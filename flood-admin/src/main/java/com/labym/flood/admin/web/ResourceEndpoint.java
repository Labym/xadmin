package com.labym.flood.admin.web;

import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.service.ResourceService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menus")
public class ResourceEndpoint {

    private final ResourceService resourceService;

    public ResourceEndpoint(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/current")
    public ResponseEntity currentUserMenus(){
        return ResponseEntity.ok(resourceService.currentUserMenus());
    }

    @Timed
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createResource(@RequestBody Resource resource){
        resourceService.create(resource);
    }
}
