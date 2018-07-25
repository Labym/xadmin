package com.labym.flood.admin.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labym.flood.admin.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TB_PREFIX + "DICTIONARY",
        indexes = {
                @Index(columnList = "name", unique = true),
                @Index(columnList = "code", unique = true),
                @Index(columnList = "group_id")
        }
)
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String code;
    @ManyToOne
    @JoinColumn(name="group_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private DictionaryGroup group;
    private String value;
}
