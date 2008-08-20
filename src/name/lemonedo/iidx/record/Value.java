package name.lemonedo.iidx.record;

/**
 * An integer value, capable of being undefined.  
 * 
 * @author LEE Chungmin
 */
public interface Value extends Comparable<Value> {

  /**
   * Returns <code>true</code> if it is defined, i.e., its value is in the valid
   * range.
   * 
   * @return <code>true</code> if it is defined, i.e., its value is in the valid
   *        range
   */
  boolean isDefined();

  /**
   * Returns <code>true</code> if it is undefined, i.e., its value is out of
   * range.
   * 
   * @return <code>true</code> if it is undefined, i.e., its value is out of
   *        range
   */
  boolean isUndefined();

  /**
   * Returns an integer of the same value as this. If this is undefined, the
   * result of invoking this method is implementation-dependent.
   * 
   * @return an integer of the same value as this
   */
  int toInt();

  /**
   * Returns a string representing this. If this is undefined, some special
   * string may be returned.
   * 
   * @return a string representing this
   */
  String toString();
}
