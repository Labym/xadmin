package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TB_PREFIX + "DICTIONARY_GROUP",
        indexes = {
                @Index(columnList = "name", unique = true),
                @Index(columnList = "code", unique = true)
        }
)
public class DictionaryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String code;
    private Set<Dictionary> dictionaries;
}
