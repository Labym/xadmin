package com.labym.flood.admin.common.util;

import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.config.FloodConstants;
import com.labym.flood.exception.FloodException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.labym.flood.admin.constant.StatusCode.BAD_REQUEST;

public class UserUtils {

    private UserUtils(){}

    private static final Pattern PHONE_PATTERN=Pattern.compile(FloodConstants.PHONE_PATTERN);
    private static final Pattern EMAIL_PATTERN=Pattern.compile(FloodConstants.EMAIL_PATTERN);

    public static AccountType accountType(String login){

        if(StringUtils.isEmpty(login)){
            throw new FloodException(BAD_REQUEST,"账号为空");
        }

        if (login.matches(FloodConstants.PHONE_PATTERN)) {
            return AccountType.PHONE;
        }

        if (login.matches(FloodConstants.EMAIL_PATTERN)) {
            return AccountType.EMAIL;
        }

        throw new FloodException(BAD_REQUEST,"账号必须是手机号码或者Email");

    }
}
