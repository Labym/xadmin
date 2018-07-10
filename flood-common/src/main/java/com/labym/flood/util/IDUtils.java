package com.labym.flood.util;

import java.util.UUID;

public class IDUtils {
    private IDUtils(){}

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
