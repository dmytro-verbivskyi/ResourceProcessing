package dverbovskiy.util;

public class DebugUtil {
    public static String START = "===== start =====";
    public static String END = "====== end ======";

    public static String start(String methodName) {
        return "start: " + methodName;
    }
    public static String end(String methodName) {
        return "===== end  : " + methodName;
    }
}
