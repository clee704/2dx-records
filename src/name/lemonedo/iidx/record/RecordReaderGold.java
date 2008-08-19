package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

import name.lemonedo.util.Pair;

/**
 * 
 * @author LEE Chungmin
 */
class RecordReaderGold extends AbstractRecordReader {

  RecordReaderGold(File saveFile) throws IOException {
    super(Version.GOLD, saveFile, "gold.txt", new Pair<Integer>(99480, 20));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    if ((b[0][13] & 0xFF) == 0x90)
      return null;
    return Record.newBuilder().song(song).playMode(mode)
        .djLevel(Record.DjLevel.values()[down(b[0][13], 4, 4)])
        .clear(Record.Clear.values()[down(b[0][17], 3, 5)])
        .exScore(up(b[0][11], 6, 8) + up(b[0][10], 8, 0))
        .maxCombo(up(b[0][13], 4, 10) + up(b[0][12], 8, 2) +
            down(b[0][11], 2, 6))
        .missCount(up(b[0][15], 6, 8) + up(b[0][14], 8, 0))
        .playCount(up(b[0][17], 5, 9) + up(b[0][16], 8, 1) +
            down(b[0][15], 1, 7))
        .just(up(b[0][3], 6, 8) + up(b[0][2], 8, 0))
        .great(up(b[0][5], 4, 10) + up(b[0][4], 8, 2) + down(b[0][3], 2, 6))
        .good(up(b[0][7], 6, 8) + up(b[0][6], 8, 0))
        .bad(up(b[0][9], 4, 10) + up(b[0][8], 8, 2) + down(b[0][7], 2, 6))
        .poor(up(b[0][19], 6, 8) + up(b[0][18], 8, 0)).build();
  }

  private static final int[] bits =
      {0x00, 0x01, 0x03, 0x07, 0x0F, 0x1F, 0x3F, 0x7F, 0xFF};

  private int up(byte b, int n, int s) {
    if (n < 0 || n > 8 || s < 0)
      throw new IllegalArgumentException("out of range");
    else
      return (b & bits[n]) << s;
  }

  private int down(byte b, int n, int s) {
    if (n < 0 || n > 8 || s < 0)
      throw new IllegalArgumentException("out of range");
    else
      return (b >>> s) & bits[n];
  }
}
