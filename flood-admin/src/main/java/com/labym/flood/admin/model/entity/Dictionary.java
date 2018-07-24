package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TB_PREFIX + "DICTIONARY",
        indexes = {
                @Index(columnList = "name", unique = true),
                @Index(columnList = "code", unique = true),
                @Index(columnList = "resource_type")
        }
)
public class Dictionary {
    private Long id;
    private String name;
    private String group;
    private String value;
}
