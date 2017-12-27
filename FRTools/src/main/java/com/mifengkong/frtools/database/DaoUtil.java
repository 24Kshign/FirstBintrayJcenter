package com.mifengkong.frtools.database;

import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by jiangyongxing on 2017/7/9.
 * 描述：
 */

public class DaoUtil {


    private DaoUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text";
        } else if (type.contains("int")) {
            value = " integer";
        } else if (type.contains("boolean")) {
            value = " boolean";
        } else if (type.contains("float")) {
            value = " float";
        } else if (type.contains("double")) {
            value = " double";
        } else if (type.contains("char")) {
            value = " varchar";
        } else if (type.contains("long")) {
            value = " long";
        } else if (type.contains("IncrementalChange")) {
            value = " text";
        } else if (TextUtils.isEmpty(type))
            value = " ";
        return value;
    }

    public static String capitalize(String string) {
        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }
}
