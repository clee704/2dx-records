package name.lemonedo.iidx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public abstract class RecordReader {

  protected final File saveFile;
  protected final List<Song> songList;
  protected final EnumMap<PlayMode, List<Record>> records;

  private final Version version;

  protected RecordReader(File saveFile, List<Song> songList, Version version) {
    this.saveFile = saveFile;
    this.songList = Collections.unmodifiableList(new ArrayList<Song>(songList));
    this.records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
    this.version = version;
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
