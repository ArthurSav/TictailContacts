package io.c0nnector.github.tictailcontacts.util;

public final class Strings {


    private Strings() {
        // No instances.
    }

    public static boolean isBlank(CharSequence string) {
        return (string == null || string.toString().trim().length() == 0);
    }

    public static boolean isNotBlank(CharSequence string) {
        return !isBlank(string);
    }

    public static String valueOrDefault(String string, String defaultString) {
        return isBlank(string) ? defaultString : string;
    }

    public static String truncateAt(String string, int length) {
        return string.length() > length ? string.substring(0, length) : string;
    }

    public static String removeSpaces(String string) {
        return isNotBlank(string) ? string.replace(" ", "") : "";
    }
}
