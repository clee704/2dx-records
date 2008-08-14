package name.lemonedo.util;

import java.util.ArrayList;
import java.util.List;

public final class Algorithms {

    public static <T> List<T> findIf(List<T> list,
                                     Method<Boolean, ? super T> pred) {
        List<T> newList = new ArrayList<T>();
        for (T e : list)
            if (pred.eval(e))
                newList.add(e);
        return newList;
    }
}
