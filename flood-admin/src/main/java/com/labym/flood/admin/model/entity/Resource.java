package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import com.labym.flood.admin.constant.EntityState;
import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.converter.MapToJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(length = 100)
    private String name;
    @Column(length = 1000)
    private String url;
    @Column(length = 100)
    private String code;
    private Long parentId;
    private Double sort;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType type;
    private Long createBy;

    private LocalDateTime createAt;

    @Lob
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> extensions;
}
