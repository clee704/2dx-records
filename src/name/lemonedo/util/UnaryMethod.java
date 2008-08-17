package name.lemonedo.util;

public interface UnaryMethod<R, T> {

  public R eval(T object);
}
