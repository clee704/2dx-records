package name.lemonedo.util;

public interface UnaryPredicate<T> extends UnaryMethod<Boolean, T> {

  public Boolean eval(T object);
}
