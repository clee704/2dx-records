package name.lemonedo.iidx;

public class Value {

  private final int value;
  private final int min;
  private final int max;

  public Value(int value, int min, int max) {
    this.value = value;
    this.min = min;
    this.max = max;
  }

  public boolean isDefined() {
    return value >= min && value <= max;
  }

  public boolean isUndefined() {
    return !isDefined();
  }

  public int toInt() {
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
