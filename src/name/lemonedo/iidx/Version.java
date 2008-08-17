package name.lemonedo.iidx;

import java.io.File;

public enum Version {

  HAPPY_SKY, DISTORTED, GOLD;

  /**
   * Returns a <code>Version</code> which corresponds with the specified save
   * file, or <code>null</code> if the file is not of one of the supported
   * versions.
   * 
   * @param saveFile the save file
   * @return a <code>Version</code> which corresponds with the specified save
   *         file, or <code>null</code> if the file is not of one of the
   *         supported versions.
   */
  public static Version getVersion(File saveFile) {
    long length = saveFile.length();
    if (length == 418816)
      return HAPPY_SKY;
    else if (length == 369664)
      return DISTORTED;
    else if (length == 387072)
      return GOLD;
    else
      return null;
  }

  @Override
  public String toString() {
    switch (this) {
    case HAPPY_SKY:
      return "beatmaniaIIDX 12 Happy Sky";
    case DISTORTED:
      return "beatmaniaIIDX 13 DistorteD";
    case GOLD:
      return "beatmaniaIIDX 14 GOLD";
    default:
      return null;
    }
  }
}
