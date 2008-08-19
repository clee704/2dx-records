package name.lemonedo.util;

public class Pair<E> {

  private final E e1;
  private final E e2;

  public Pair(E e1, E e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public E getFirst() {
    return e1;
  }

  public E getSecond() {
    return e2;
  }
}
