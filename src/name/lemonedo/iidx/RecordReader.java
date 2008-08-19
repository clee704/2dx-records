package name.lemonedo.iidx;

import java.util.List;

/**
 * 
 * @author LEE Chungmin
 */
public interface RecordReader {

  /**
   * Returns the version of this reader.
   * 
   * @return the version of this reader.
   */
  public Version getVersion();

  /**
   * Returns a list of records of the specified play mode.
   * 
   * @param playMode a play mode of records to read
   * @return a list of records
   */
  public List<Record> read(PlayMode mode);
}
