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

  public static <T> List<T> filter(Iterable<T> elements,
                                   UnaryPredicate<? super T> p) {
    List<T> filtered = new ArrayList<T>();
    for (T e : elements)
      if (p.eval(e))
        filtered.add(e);
    return filtered;
  }

  public static <T> T findIf(Iterable<T> elements,
                             UnaryPredicate<? super T> p) {
    for (T e : elements)
      if (p.eval(e))
        return e;
    return null;
  }
}
