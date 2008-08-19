package name.lemonedo.util;

public interface UnaryPredicate<T> extends UnaryFunction<Boolean, T> {

  public Boolean eval(T object);
}
