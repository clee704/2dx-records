package name.lemonedo.iidx.record;

import static name.lemonedo.iidx.record.PlayMode.DA;
import static name.lemonedo.iidx.record.PlayMode.DH;
import static name.lemonedo.iidx.record.PlayMode.DN;
import static name.lemonedo.iidx.record.PlayMode.SA;
import static name.lemonedo.iidx.record.PlayMode.SH;
import static name.lemonedo.iidx.record.PlayMode.SN;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import name.lemonedo.util.Pair;

/**
 * 
 * @author LEE Chungmin
 */
abstract class AbstractRecordReader implements RecordReader {

  private final Version version;
  private final byte[] bin;
  private final List<Pair<Integer>> metaInfo;
  private final List<Song> songList;
  private final EnumMap<PlayMode, List<Record>> records;

  AbstractRecordReader(Version version, File psuFile, String songListFileName,
                       Pair<Integer>... metaInfo)
  throws IOException {
    this.version = version;
    this.bin = getContent(psuFile);
    this.metaInfo = createImmutableList(metaInfo);
    this.songList = SongListReader.read(songListFileName);
    this.records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
  }

  /**
   * Returns an byte array that contains the specified file's data.
   * 
   * @param f the file
   * @return an byte array that contains the specified file's data.
   * @throws IOException if an I/O error occurs
   */
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

  /**
   * Returns an immutable <code>List</code> of the specified elements.
   * 
   * @param <T> the type of elements
   * @param elements the elements
   * @return an immutable <code>List</code> of the specified elements.
   */
  private static <T> List<T> createImmutableList(T... elements) {
    List<T> list = new ArrayList<T>(elements.length);
    for (T e : elements)
      list.add(e);
    return Collections.unmodifiableList(list);
  }

  @Override
  public Version getVersion() {
    return version;
  }

  @Override
  public List<Record> read(PlayMode playMode) {
    synchronized (records) {
      if (records.isEmpty())
        read();
    }
    return records.get(playMode);
  }

  private void read() {
    byte[][] b = new byte[metaInfo.size()][];
    int[] pos = new int[metaInfo.size()];
    for (int i = 0; i < metaInfo.size(); i++) {
      b[i] = new byte[metaInfo.get(i).getSecond()];
      pos[i] = metaInfo.get(i).getFirst();
    }

    for (PlayMode mode : parseOrder(version)) {
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

  private PlayMode[] parseOrder(Version version) {
    switch (version) {
    case IIDX_RED:
      return new PlayMode[] {SH, SN, SA, DH, DN, DA};
    default:
      return PlayMode.values();
    }
  }

  /**
   * Returns a <code>Record</code> parsed from the bytes, or <code>null</code>
   * if the bytes has no record information.
   * 
   * @param b bytes to be examined
   * @param song a song of the record
   * @param mode a play mode of the record
   * @return a <code>Record</code> if it parsed, or <code>null</code> if the
   *        bytes has no record information
   */
  abstract Record parseRecord(byte[][] b, Song song, PlayMode mode);
}
