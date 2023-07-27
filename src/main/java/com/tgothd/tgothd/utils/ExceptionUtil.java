package com.tgothd.tgothd.utils;

/**
 * @author: ShrJanLan
 * @date: 2023/6/26 14:21
 * @description:
 */
public class ExceptionUtil {

    public static String getMessage(Exception e) {
        String message = e.getMessage();
        return CommonUtil.isEmpty(message) ? e.toString() : message;
    }

}
