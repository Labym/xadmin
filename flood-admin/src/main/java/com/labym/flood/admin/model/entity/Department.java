package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = Constants.TB_PREFIX + "DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100,nullable = false)
    private String name;

}
