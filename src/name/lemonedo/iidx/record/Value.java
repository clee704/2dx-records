package name.lemonedo.iidx.record;

public interface Value extends Comparable<Value> {

  boolean isDefined();
  boolean isUndefined();
  int toInt();
  String toString();
}
