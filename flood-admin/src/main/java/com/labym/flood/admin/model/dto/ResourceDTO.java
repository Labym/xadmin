package com.labym.flood.admin.model.dto;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.entity.Resource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ResourceDTO {


    @NotNull
    private Long id;
    @NotNull
    @Size(min = 1,max = 100)
    private String name;
    private String url;
    private String code;
    private Long parentId;
    private Double sort=0D;

    @NotNull
    private ResourceType type;
    @ApiModelProperty(readOnly = true)
    private Long createBy;
    @ApiModelProperty(readOnly = true)
    private LocalDateTime createAt;
    private Map<String, Object> extensions;
}
