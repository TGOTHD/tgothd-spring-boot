package com.tgothd.tgothd.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author ShrJanLan
 * @date 2023/7/4 9:07
 * @description
 */
@Component
public class I18nUtil {

    private static MessageSource messageSource;

    public I18nUtil(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }

    /**
     * 获取国际化翻译值
     */
    public static String get(String key) {
        return getMessage(key, null);
    }

    /**
     * 获取国际化翻译值
     */
    public static String get(String key, Locale locale) {
        return getMessage(key, null, locale);
    }

    /**
     * 获取国际化翻译值(占位符)
     */
    private static String getMessage(String key,Object[] args) {
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }

    /**
     * 获取国际化翻译值(占位符)
     */
    private static String getMessage(String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            return key;
        }
    }

}
