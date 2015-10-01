package io.c0nnector.github.tictailcontacts.util;

/**
 * Util class to check values
 */
public final class Val {

    private Val(){
        //no instances
    }

    /**
     * True if not null
     * @param value object to be checked
     */
    public static boolean notNull(Object value){
        return value !=null;
    }

    /**
     * True if null
     * @param value object to be checked
     * @return
     */
    public static boolean isNull(Object value){
        return value == null;
    }

    /**
     * Checks if all list objects are non null
     * @param objects
     * @return
     */
    public static boolean containsNull(Object... objects) {

        for (Object object: objects) {

            if (object == null) return true;
        }

        return false;
    }
}
