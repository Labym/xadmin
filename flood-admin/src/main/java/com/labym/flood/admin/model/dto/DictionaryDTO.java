package com.labym.flood.admin.model.dto;

import com.labym.flood.admin.model.entity.Dictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictionaryDTO {
    private String name;
    private String code;
    private Long groupId;
    private String value;
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;

    public static DictionaryDTO from(Dictionary dictionary){
        if(null==dictionary){
            return null;
        }

        DictionaryDTO dto=new DictionaryDTO();

        dto.setId(dictionary.getId());
        dto.setCode(dictionary.getCode());
        //dto.setGroupId(dictionary.getGroup());

        dto.setName(dictionary.getName());
        dto.setValue(dictionary.getValue());

        return dto;
    }
}
