package name.lemonedo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author LEE Chungmin
 */
public final class Algorithms {

  // prevent instatiation
  private Algorithms() {}

  public static <T> List<T> filter(List<T> list, UnaryPredicate<? super T> p) {
    List<T> filtered = new ArrayList<T>();
    for (T e : list)
      if (p.eval(e))
        filtered.add(e);
    return filtered;
  }
}
