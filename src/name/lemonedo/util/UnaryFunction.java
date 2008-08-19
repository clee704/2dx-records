package name.lemonedo.util;

/**
 * 
 * @author LEE Chungmin
 */
public interface UnaryFunction<R, T> {

  public R eval(T object);
}
