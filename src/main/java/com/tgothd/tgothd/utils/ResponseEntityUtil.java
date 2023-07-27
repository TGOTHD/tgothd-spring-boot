package com.tgothd.tgothd.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ShrJanLan
 * @date: 2023/6/26 15:48
 * @description:
 */
public class ResponseEntityUtil {

    private final static String CODE = "code";
    private final static String MESSAGE = "msg";

    public static ResponseEntity error(Exception e) {
        return message(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtil.getMessage(e));
    }

    public static Map getResultMap(int code,String msg) {
        Map vo = new HashMap();
        vo.put(CODE, code);
        vo.put(MESSAGE, msg);
        return vo;
    }

    public static ResponseEntity message(HttpStatus status,String msg) {
        return new ResponseEntity(getResultMap(status.value(), msg), status);
    }

}
