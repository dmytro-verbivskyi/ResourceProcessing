package dverbovskiy.debugTraining;

import java.util.*;

public class Test {
    public static void main(String[] a) {
        A aa = new A();

        List temp = aa.pubList;

        int yrt = 0;

        temp.set(0, 123);
        yrt = 3;
    }
}

class A {
    private List<Integer> list =  new ArrayList<Integer>();
    public List<Integer> pubList = Collections.unmodifiableList(list);

    A() {
        list.add(100);
        list.add(200);
        list.add(300);
    }
}
