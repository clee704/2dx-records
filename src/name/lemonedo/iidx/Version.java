package name.lemonedo.iidx;

/**
 * 
 * @author LEE Chungmin
 */
public enum Version {

  HAPPY_SKY, DISTORTED, GOLD;

  @Override
  public String toString() {
    switch (this) {
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
