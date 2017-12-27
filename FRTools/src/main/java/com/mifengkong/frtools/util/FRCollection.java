package com.mifengkong.frtools.util;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * collection接口方法的一些封装
 */
public class FRCollection {
    private FRCollection() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean isEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty() || collection.size() <= 0;
    }

    public static int size(@Nullable Collection collection) {
        return collection != null ? collection.size() : 0;
    }

    public static void clear(@Nullable Collection collection) {
        if (collection != null) {
            collection.clear();
        }
    }

    public static String appendAsString(Collection collection, String separator) {
        if (FRCollection.isEmpty(collection)) {
            return FRString.EMPTY_STRING;
        }
        if (FRString.isEmpty(separator)) {
            return FRString.EMPTY_STRING;
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : collection) {
            String string = FRObject.toString(object);
            if (FRString.isEmpty(string)) {
                continue;
            }
            sb.append(string).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String appendAsString(Collection collection) {
        return appendAsString(collection, ",");
    }

    public static String appendAsString(String... strings) {
        if (null == strings || strings.length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String separator = strings[strings.length - 1];
        for (int i = 0; i < strings.length - 1; i++) {
            if (i == strings.length - 2) {
                sb.append(FRString.valueOf(strings[i]));
            } else {
                sb.append(FRString.valueOf(strings[i])).append(separator);
            }
        }
        return sb.toString();
    }

    public static LinkedList<String> splitStringToLinkedList(String string, String separator) {
        if (FRString.isEmpty(string)) {
            return null;
        }
        if (FRString.isEmpty(separator)) {
            return null;
        }
        LinkedList<String> list = new LinkedList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, separator);
        while (stringTokenizer.hasMoreElements()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }
}