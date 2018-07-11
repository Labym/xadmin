package com.labym.flood.util.tree;

import java.util.List;


public interface Node<ENTITY,ID> {
    ID getId();
    ID getParentId();
    List<ENTITY> getChildren();
}
