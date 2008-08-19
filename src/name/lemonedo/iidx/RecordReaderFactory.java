package name.lemonedo.iidx;

import static name.lemonedo.iidx.Version.DISTORTED;
import static name.lemonedo.iidx.Version.GOLD;
import static name.lemonedo.iidx.Version.HAPPY_SKY;

import java.io.File;
import java.io.IOException;

public class RecordReaderFactory {

  /**
   * Returns <code>true</code> if the specified PSU file is of the supported
   * versions.
   * 
   * @param aPsuFile a PSU file
   * @return <code>true</code> if the specified PSU file is of the supported
   *         versions.
   */
  public static boolean isSupportedVersion(File aPsuFile) {
    return getVersion(aPsuFile) != null;
  }

  /**
   * Returns a <code>Version</code> which corresponds with the specified PSU
   * file, or <code>null</code> if the file is not of of the supported
   * versions.
   * 
   * @param aPsuFile a PSU file
   * @return a <code>Version</code> which corresponds with the specified PSU
   *         file, or <code>null</code> if the file is not of the supported
   *         versions.
   */
  public static Version getVersion(File aPsuFile) {
    long length = aPsuFile.length();
    if (length == 418816)
      return HAPPY_SKY;
    if (length == 369664)
      return DISTORTED;
    if (length == 387072)
      return GOLD;
    else
      return null;
  }

  /**
   * Returns an <code>RecordReader</code> with the specified save file.
   * 
   * @param psuFile the save file
   * @return an <code>RecordReader</code> with the specified save file as a
   *        source
   * @throws IOException if an I/O error occurs
   * @throws IllegalArgumentException if the save file is not of one of the
   *        supported versions
   */
  public static RecordReader newRecordReader(File aPsuFile) throws IOException {
    if (!isSupportedVersion(aPsuFile))
      throw new IllegalArgumentException("unsupported version");

    switch (getVersion(aPsuFile)) {
    case HAPPY_SKY:
      return new RecordReaderHappySky(aPsuFile);
    case DISTORTED:
      return new RecordReaderDistorted(aPsuFile);
    case GOLD:
      return new RecordReaderGold(aPsuFile);
    default:
      return null;
    }
  }
}
