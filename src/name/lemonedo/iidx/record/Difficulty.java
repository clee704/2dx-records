package name.lemonedo.iidx.record;

/**
 * Difficulty for a song with a play mode. The valid range is
 * 1-12 (inclusive).
 * 
 * @author LEE Chungmin
 */
public class Difficulty implements Value {

  /**
   * A constant for undefined <code>Difficulty</code>.
   */
  public static final Difficulty UNDEFINED = new Difficulty(-1, false);

  private final int value;
  private final boolean plus;

  private Difficulty(int value, boolean plus) {
    this.value = value;
    this.plus = plus;
  }

  /**
   * Returns a <code>Difficulty</code> for <code>value</code>. It will be
   * undefined if <code>value</code> is not in the range 1-12 (inclusive).
   * 
   * @param value a value
   * @return a <code>Difficulty</code> for <code>value</code>
   */
  public static Difficulty newInstance(int value) {
    return newInstance(value, false);
  }

  private static Difficulty newInstance(int value, boolean plus) {
    if (value < 1 || value > 12)
      return UNDEFINED;
    else
      return new Difficulty(value, plus);
  }

  /**
   * Returns a <code>Difficulty</code> for <code>value</code>. It will be
   * undefined if <code>value</code> is not in the range 1-12 (inclusive).
   * A '+' sign in <code>value</code> will be properly interpreted. For example,
   * '8+' will be treated as '9'. (for {@link #toString()} again '8+') 
   * 
   * @param value a value
   * @return a <code>Difficulty</code> for <code>value</code>
   */
  public static Difficulty newInstance(String value) {
    if (value.matches("\\d+"))
      return newInstance(Integer.parseInt(value), false);
    else if (value.matches("\\d+\\+"))
      return newInstance(
          Integer.parseInt(value.substring(0, value.length() - 1)) + 1, true);
    else
      return UNDEFINED;
  }

  public boolean isDefined() {
    return !isUndefined();
  }

  public boolean isUndefined() {
    return this == UNDEFINED;
  }

  public int toInt() {
    if (isUndefined())
      throw new IllegalStateException("undefined");
    return value;
  }

  @Override
  public String toString() {
    if (isUndefined())
      return "-";
    if (plus)
      return String.valueOf(value - 1) + "+";
    else
      return String.valueOf(value);
  }

  @Override
  public boolean equals(Object o) {
    return (o == this) || ((o instanceof Difficulty)
        && ((Difficulty) o).value == value && o != UNDEFINED);
  }

  @Override
  public int compareTo(Value o) {
    if (isUndefined())
      if (o.isUndefined())
        return 0;
      else
        return 1;
    if (o.isUndefined())
      return -1;
    return toInt() - o.toInt();
  }
}
