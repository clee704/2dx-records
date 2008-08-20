package name.lemonedo.iidx.record;

/**
 * 
 * @author LEE Chungmin
 */
public class CommonValue implements Value {

  public static final CommonValue UNDEFINED = new CommonValue(-1);

  private final int value;

  private CommonValue(int value) {
    this.value = value;
  }

  public static CommonValue newInstance(int value) {
    if (value < 0 || value > 9999)
      return UNDEFINED;
    else
      return new CommonValue(value);
  }

  public static CommonValue newInstance(String value) {
    if (value.matches("\\d+"))
      return newInstance(Integer.parseInt(value));
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
    else
      return String.valueOf(value);
  }

  @Override
  public boolean equals(Object o) {
    return (o == this) || ((o instanceof CommonValue)
        && ((CommonValue) o).value == value && o != UNDEFINED);
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
