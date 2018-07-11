package com.labym.flood.admin.model.vm;

import com.labym.flood.util.tree.Node;
import lombok.Data;

import java.util.List;

@Data
public class MenuVM  implements Node<MenuVM,Long> {
    private Long id;
    private Long parentId;
    List<MenuVM> children;
}

