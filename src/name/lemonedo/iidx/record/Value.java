package name.lemonedo.iidx.record;

/**
 * 
 * @author LEE Chungmin
 */
public class Value {

  public static final Value UNDEFINED = new Value(-1, 0, 0);

  private final int value;
  private final int min;
  private final int max;

  private Value(int value, int min, int max) {
    this.value = value;
    this.min = min;
    this.max = max;
  }

  public static Value create(int value, int min, int max) {
    if (value < min || value > max)
      return UNDEFINED;
    else
      return new Value(value, min, max);
  }

  public boolean isDefined() {
    return !isUndefined();
  }

  public boolean isUndefined() {
    return this == UNDEFINED || value < min || value > max;
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
}
