package name.lemonedo.iidx;

import java.io.File;

public class RecordReaderFactory {

  /**
   * Returns an <code>RecordReader</code> with the specified save file.
   * 
   * @param saveFile the save file
   * @return an <code>RecordReader</code> with the specified save file as a
   *         source
   * @throws IllegalArgumentException if the save file is not of one of the
   *         supported versions
   * @see Version#getVersion(File)
   */
  public static RecordReader create(File saveFile) {
    Version v = Version.getVersion(saveFile);
    if (v == null)
      throw new IllegalArgumentException("unsupported version");
    switch (v) {
    case HAPPY_SKY:
      return new RecordReaderHappySky(saveFile);
    case DISTORTED:
      return new RecordReaderDistorted(saveFile);
    case GOLD:
      return new RecordReaderGold(saveFile);
    default:
      throw new RuntimeException("cannot be reached on release");
    }
  }
}
