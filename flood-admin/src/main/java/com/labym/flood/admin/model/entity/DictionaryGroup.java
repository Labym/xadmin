package com.labym.flood.admin.model.entity;

import com.labym.flood.admin.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
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
@ToString(exclude={"dictionaries"})
public class DictionaryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 100)
    private String name;
    @NotNull
    @Column(length = 100)
    private String code;

    @ApiModelProperty(readOnly = true)
    private ZonedDateTime createAt;
    @ApiModelProperty(readOnly = true)
    @OneToMany(mappedBy="group")
    private Set<Dictionary> dictionaries;
}
