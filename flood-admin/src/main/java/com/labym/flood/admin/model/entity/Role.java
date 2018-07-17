package com.labym.flood.admin.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labym.flood.admin.constant.Constants;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = Constants.TB_PREFIX + "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 32, nullable = false)
    private String code;
    private String description;

    private Long createBy;
    private ZonedDateTime createAt;

    @ManyToMany
    @JoinTable( name = Constants.TB_PREFIX+"ROLE_RESOURCE",
            joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id",referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<Resource> resources;
}
