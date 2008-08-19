package name.lemonedo.util;

public interface UnaryFunction<R, T> {

  public R eval(T object);
}
