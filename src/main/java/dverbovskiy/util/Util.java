package dverbovskiy.util;

/**
 * User: dverbovskiy
 * Date: 06.02.13
 */
public class Util {

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        // gotten from: http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    public static <T> T as(Class<T> t, Object o) {
        // gotten from: http://stackoverflow.com/questions/1034204/equivalent-of-the-c-sharp-keyword-as-in-java
        return t.isInstance(o) ? t.cast(o) : null;
        /* Usage:
            MyType a = as(MyType.class, new MyType());  // 'a' is not null
            MyType b = as(MyType.class, "");            // b is null
         */
    }

}
