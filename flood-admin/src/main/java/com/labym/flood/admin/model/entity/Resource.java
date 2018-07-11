package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.config.FloodConstants;
import com.labym.flood.converter.MapToJsonConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;


@Data
@Entity
@Table(name = Constants.TB_PREFIX+"RESOURCE")
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
    private Instant createAt;
    private Long createBy;

    @Lob
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> extensions;
}
