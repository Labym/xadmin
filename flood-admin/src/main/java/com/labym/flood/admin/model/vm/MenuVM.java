package com.labym.flood.admin.model.vm;

import com.labym.flood.admin.constant.ResourceType;
import com.labym.flood.admin.model.entity.Resource;
import com.labym.flood.converter.MapToJsonConverter;
import com.labym.flood.util.tree.Node;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class MenuVM  implements Comparable<MenuVM>, Node<MenuVM,Long> {

    private Long id;
    private String name;
    private String url;
    private String code;
    private Long parentId;
    private Double sort;
    private ResourceType type;
    private Map<String, Object> extensions;
    private List<MenuVM> children;

    public List<MenuVM> children(){
        if(null==children){
            children=new ArrayList<>();
        }
        return children;
    }

    @Override
    public int compareTo(MenuVM o) {
        if(null==o){
            return 0;
        }
        Double sort = this.getSort()- o.getSort();
        return sort<0?-1:(sort>0)?1:0;
    }

    public MenuVM(Resource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        this.url = resource.getUrl();
        this.code = resource.getCode();
        this.parentId = resource.getParentId();
        this.sort = resource.getSort();
        this.type = resource.getType();
        this.extensions = resource.getExtensions();
    }
}

