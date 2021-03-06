package name.lemonedo.iidx.record;

import static name.lemonedo.iidx.record.Version.DISTORTED;
import static name.lemonedo.iidx.record.Version.GOLD;
import static name.lemonedo.iidx.record.Version.HAPPY_SKY;
import static name.lemonedo.iidx.record.Version.IIDX_RED;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author LEE Chungmin
 */
public class RecordReaderFactory {

  // prevent instantiation
  private RecordReaderFactory() {}

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
   * file, or <code>null</code> if the file is not of the supported versions.
   * 
   * @param aPsuFile a PSU file
   * @return a <code>Version</code> which corresponds with the specified PSU
   *         file, or <code>null</code> if the file is not of the supported
   *         versions.
   */
  public static Version getVersion(File aPsuFile) {
    long length = aPsuFile.length();
    if (length == 403456)
      return IIDX_RED;
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
   * @param aPsuFile the save file
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
    case IIDX_RED:
      return new RecordReaderIidxRed(aPsuFile);
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
