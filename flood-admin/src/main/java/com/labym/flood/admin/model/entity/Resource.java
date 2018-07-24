package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import com.labym.flood.admin.constant.EntityState;
import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.converter.MapToJsonConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TB_PREFIX + "RESOURCE",
        indexes = {
                @Index(columnList = "name", unique = true),
                @Index(columnList = "code", unique = true),
                @Index(columnList = "resource_type")
        }
)
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1,max = 100)
    @Column(length = 100)
    private String name;
    @Column(length = 1000)
    private String url;
    @Column(length = 100)
    private String code;
    private Long parentId;
    private Double sort;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType type;
    @ApiModelProperty(readOnly = true)
    private Long createBy;
    @ApiModelProperty(readOnly = true)
    private LocalDateTime createAt;

    @Lob
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> extensions;
}
