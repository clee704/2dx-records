package name.lemonedo.iidx.record;

/**
 * 
 * @author LEE Chungmin
 */
public enum Version {

  IIDX_RED, HAPPY_SKY, DISTORTED, GOLD;

  @Override
  public String toString() {
    switch (this) {
    case IIDX_RED:
      return "beatmaniaIIDX 11 IIDX RED";
    case HAPPY_SKY:
      return "beatmaniaIIDX 12 HAPPY SKY";
    case DISTORTED:
      return "beatmaniaIIDX 13 DistorteD";
    case GOLD:
      return "beatmaniaIIDX 14 GOLD";
    default:
      return null;
    }
  }
}
