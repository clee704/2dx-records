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

  public RecordReader(File saveFile, List<Song> songList) {
    this.saveFile = saveFile;
    this.songList = Collections.unmodifiableList(new ArrayList<Song>(songList));
    this.records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
  }

  public abstract List<Record> read(PlayMode playMode) throws IOException;
}
