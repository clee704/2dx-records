package name.lemonedo.iidx.record;

/**
 * 
 * @author LEE Chungmin
 */
public enum PlayMode {

  SN, SH, SA, DN, DH, DA;

  public boolean isSingleMode() {
    switch (this) {
    case SN:
    case SH:
    case SA:
      return true;
    default:
      return false;
    }
  }

  public boolean isDoubleMode() {
    return !isSingleMode();
  }
}
