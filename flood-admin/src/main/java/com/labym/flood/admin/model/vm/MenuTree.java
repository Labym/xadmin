package com.labym.flood.admin.model.vm;

import com.labym.flood.admin.constant.Constants;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class MenuTree extends ArrayList<MenuVM> {

    private MenuTree(int initialCapacity) {
        super(initialCapacity);
    }

    public MenuTree(Collection<? extends MenuVM> c) {
        super(c);
    }
    public static MenuTree buildTree(List<MenuVM> menus){
        return buildTree(menus,true);
    }
    public static MenuTree buildTree(List<MenuVM> menus,boolean emptyChildren){
        if(CollectionUtils.isEmpty(menus)){
            return new MenuTree(0);
        }

        Collections.sort(menus);
        MenuTree tree=new MenuTree(menus.size());
        menus.forEach((menu)->{
            if(Constants.ROOT_RESOURCE_PARENT_ID.equals(menu.getParentId())){
                buildChildren(menu,menus,emptyChildren);
                tree.add(menu);
            }
        });
        return tree;
    }

    private static void buildChildren(MenuVM parent,List<MenuVM> menus,boolean emptyChildren){
        menus.forEach((menuVM -> {
            if(parent.getId().equals(menuVM.getParentId())){
                buildChildren(menuVM,menus,emptyChildren);
                parent.children().add(menuVM);
            }
        }));

        if (emptyChildren&&CollectionUtils.isEmpty(parent.getChildren())) {
            parent.setChildren(Collections.emptyList());
        }

    }
}

