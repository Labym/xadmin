package com.labym.flood.admin.web;

import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.admin.service.ResourceService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @SecurityConfiguration
    public ResponseEntity currentUserMenus(){
        return ResponseEntity.ok(resourceService.currentUserMenus());
    }


    @Timed
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    public void createResource(@RequestBody Resource resource){
        resourceService.create(resource);
    }
}
