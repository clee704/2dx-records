package name.lemonedo.iidx;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class RecordReader {

  protected final File saveFile;
  protected final Map<Integer, Song> songList;
  protected final EnumMap<PlayMode, List<Record>> records;
  protected final int entrySize;

  private final Version version;

  protected RecordReader(File saveFile, List<Song> songList, int entrySize,
                         Version version) {
    this.saveFile = saveFile;
    this.songList = convert(songList);
    this.records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
    this.entrySize = entrySize;
    this.version = version;
  }

  private Map<Integer, Song> convert(List<Song> songList) {
    Map<Integer, Song> converted = new HashMap<Integer, Song>();
    for (Song s : songList)
      converted.put(s.getId() % 100, s);
    return Collections.unmodifiableMap(converted);
  }

  /**
   * Returns the version of this reader.
   * 
   * @return the version of this reader.
   */
  public Version getVersion() {
    return version;
  }

  /**
   * Returns a list of records of the specified play mode, from the save file.
   * 
   * @param playMode a play mode of records to read
   * @return a list of records
   * @throws IOException if reading failed
   */
  public abstract List<Record> read(PlayMode playMode) throws IOException;
}
