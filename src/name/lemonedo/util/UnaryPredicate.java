package name.lemonedo.util;

/**
 * 
 * @author LEE Chungmin
 */
public interface UnaryPredicate<T> extends UnaryFunction<Boolean, T> {

  public Boolean eval(T object);
}
