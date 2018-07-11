package com.labym.flood.util;



import org.springframework.security.crypto.codec.Hex;

import java.util.UUID;

public class IDUtils {
    private IDUtils(){}
    private static final String RC4_KEY="FLOOD";
    private static final String RC4_SUFFIX=":flood";
    private static final RC4Util RC4=new RC4Util(RC4_KEY);

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String encode(Long id){
        return new String(Hex.encode(RC4.crypt(Bytes.toBytes(id))));
    }

    public static Long decode(String id){
        return Bytes.toLong(RC4.crypt(Hex.decode(id)));

    }
}
