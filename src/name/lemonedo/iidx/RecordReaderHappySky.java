package name.lemonedo.iidx;

import java.io.File;
import java.io.IOException;

import name.lemonedo.util.Pair;

class RecordReaderHappySky extends AbstractRecordReader {

  RecordReaderHappySky(File saveFile) throws IOException {
    super(saveFile, "happysky.txt", Version.HAPPY_SKY,
        new Pair<Integer>(114066, 26), new Pair<Integer>(128418, 2));
  }

  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    if (b[0][20] == 9)
      return null;
    return Record.newBuilder().song(song).playMode(mode).
        djLevel(Record.DjLevel.values()[b[0][20] & 0x0F]).
        clear(parseClear(b[0][18], b[0][21])).
        exScore(((b[0][15] & 0xFF) << 8) + (b[0][14] & 0xFF)).
        maxCombo(((b[0][17] & 0xFF) << 8) + (b[0][16] & 0xFF)).
        missCount(((b[0][25] & 0xFF) << 8) + (b[0][24] & 0xFF)).
        playCount(((b[1][1] & 0xFF) << 8) + (b[1][0] & 0xFF)).
        just(((b[0][1] & 0xFF) << 8) + (b[0][0] & 0xFF)).
        great(((b[0][3] & 0xFF) << 8) + (b[0][2] & 0xFF)).
        good(((b[0][5] & 0xFF) << 8) + (b[0][4] & 0xFF)).
        bad(((b[0][7] & 0xFF) << 8) + (b[0][6] & 0xFF)).
        poor(((b[0][9] & 0xFF) << 8) + (b[0][8] & 0xFF)).build();
  }

  private Record.Clear parseClear(byte b1, byte b2) {
    switch (b1) {
    case 004:
    case 014:
      return Record.Clear.NO_CLEAR;
    case 005:
    case 015:
      switch (b2) {
      case 003:
        return Record.Clear.EASY_CLEAR;
      case 005:
      case 007:
        return Record.Clear.CLEAR;
      case 011:
      case 015:
      case 017:
        return Record.Clear.HARD_CLEAR;
      default:
        return null;
      }
    case 044:
    case 045:
    case 055:
      return Record.Clear.FULL_COMBO;
    case 046:
    case 047:
      return Record.Clear.PERFECT;
    default:
      return null;
    }
  }
}
