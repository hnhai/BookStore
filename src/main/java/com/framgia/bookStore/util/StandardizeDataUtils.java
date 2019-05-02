package com.framgia.bookStore.util;


import com.google.common.base.CharMatcher;

public class StandardizeDataUtils {

    private static final String HIBERNATE_ESCAPE_CHAR = "\\";
    public static final String PERCENT = "%";
    public static final String UNDERSCORE = "_";

    public static String standardize(String source){
        if (source == null || source.equals("")) {
            return "";
        }
        return source.trim().replaceAll("\\s+", " ");
    }

    public static String buildLikeClause(String source) {
        return source.trim().replace("\\", HIBERNATE_ESCAPE_CHAR + "\\")
                .replace(PERCENT, HIBERNATE_ESCAPE_CHAR + PERCENT)
                .replace(UNDERSCORE, HIBERNATE_ESCAPE_CHAR + UNDERSCORE);
    }
}
