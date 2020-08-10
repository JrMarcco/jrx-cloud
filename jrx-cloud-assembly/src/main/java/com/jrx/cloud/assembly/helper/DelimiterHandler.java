package com.jrx.cloud.assembly.helper;

/**
 * @author hongjc
 * @version 1.0  2020/8/10
 */
public interface DelimiterHandler {
    String ENUM_DELIMITER = "_";
    String JMS_DELIMITER = ":";

    static String convert(String s) {
        return s.replace(ENUM_DELIMITER, JMS_DELIMITER).toLowerCase();
    }

    static String convert(String s, String delimiter) {
        return s.replace(ENUM_DELIMITER, delimiter == null || "".equals(delimiter) ? JMS_DELIMITER : delimiter).toLowerCase();
    }

    static String first(String s) {
        return s.split(ENUM_DELIMITER)[0].toLowerCase();
    }

    static String remaining(String s) {
        int index = s.indexOf(ENUM_DELIMITER);
        return index == -1 ? s : convert(s.substring(index + 1));
    }
}
