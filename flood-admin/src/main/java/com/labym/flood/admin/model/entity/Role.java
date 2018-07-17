package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = Constants.TB_PREFIX + "USER")
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
}
