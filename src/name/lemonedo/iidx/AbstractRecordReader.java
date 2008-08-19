package name.lemonedo.iidx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import name.lemonedo.util.Pair;

abstract class AbstractRecordReader implements RecordReader {

  private final byte[] bin;
  private final List<Pair<Integer>> meta;
  private final List<Song> songList;
  private final EnumMap<PlayMode, List<Record>> records;

  private final Version version;

  protected AbstractRecordReader(File psuFile, String songListFileName,
                                 Version version, Pair<Integer>... meta)
  throws IOException {
    this.bin = getContent(psuFile);
    this.meta = createImmutableList(meta);
    this.songList = SongListReader.read(songListFileName);
    this.records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
    this.version = version;
  }

  private static byte[] getContent(File f) throws IOException {
    byte[] bin = new byte[(int) f.length()];
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
    try {
      in.read(bin);
      return bin;
    } finally {
      in.close();
    }
  }

  private static <T> List<T> createImmutableList(T... elements) {
    List<T> list = new ArrayList<T>(elements.length);
    for (T e : elements)
      list.add(e);
    return Collections.unmodifiableList(list);
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
   */
  public Collection<Record> read(PlayMode playMode) {
    synchronized (records) {
      if (records.isEmpty())
        read();
    }
    return records.get(playMode);
  }

  private void read() {
    byte[][] b = new byte[meta.size()][];
    int[] pos = new int[meta.size()];
    for (int i = 0; i < meta.size(); i++) {
      pos[i] = meta.get(i).getFirst();
      b[i] = new byte[meta.get(i).getSecond()];
    }
    for (PlayMode mode : PlayMode.values()) {
      records.put(mode, new ArrayList<Record>());
      for (Song song : songList) {
        fillBytesFromBin(b, pos);
        Record r = parseRecord(b, song, mode);
        if (r != null)
          records.get(mode).add(r);
      }
      records.put(mode, Collections.unmodifiableList(records.get(mode)));
    }
  }

  private void fillBytesFromBin(byte[][] b, int[] pos) {
    for (int i = 0; i < b.length; i++) {
      System.arraycopy(bin, pos[i], b[i], 0, b[i].length);
      pos[i] += b[i].length;
    }
  }

  protected abstract Record parseRecord(byte[][] b, Song song, PlayMode mode);
}
