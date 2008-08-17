package name.lemonedo.iidx;

import static name.lemonedo.iidx.Version.HAPPY_SKY;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RecordReaderHappySky extends RecordReader {

  public RecordReaderHappySky(File saveFile) {
    super(saveFile, SongListXmlParser.parse("songlist/happysky.xml"), 92,
        HAPPY_SKY);
  }

  @Override
  public List<Record> read(PlayMode playMode) throws IOException {
    synchronized (records) {
      if (records.isEmpty())
        read();
    }
    return records.get(playMode);
  }

  private void read() throws IOException {
    FileInputStream fis1;
    FileInputStream fis2;
    BufferedInputStream bis1 = null;
    BufferedInputStream bis2 = null;
    try {
      fis1 = new FileInputStream(saveFile);
      fis2 = new FileInputStream(saveFile);
      bis1 = new BufferedInputStream(fis1);
      bis2 = new BufferedInputStream(fis2);
      for (int i = 0; i < 114066; i++)
        bis1.read();
      for (int i = 0; i < 128418; i++)
        bis2.read();
      for (PlayMode m : PlayMode.values()) {
        records.put(m, new ArrayList<Record>());
        readHelper(bis1, bis2, m);
      }
    } catch (FileNotFoundException e) {
      records.clear();
      throw new IOException("cannot find " + saveFile, e);
    } catch (Exception e) {
      records.clear();
      throw new IOException("file is corrupt", e);
    } finally {
      if (bis1 != null)
        bis1.close();
      if (bis2 != null)
        bis2.close();
    }
  }

  private void readHelper(BufferedInputStream bis1, BufferedInputStream bis2,
                          PlayMode m) throws IOException {
    byte[] buf1 = new byte[26];
    byte[] buf2 = new byte[2];
    for (int i = 1; i <= entrySize; i++) {
      bis1.read(buf1);
      bis2.read(buf2);
      Record r = parseRecord(buf1, buf2, songList.get(i), m);
      if (r != null)
        records.get(m).add(r);
    }
  }

  private Record parseRecord(byte[] b1, byte[] b2, Song song, PlayMode m) {
    if (b1[20] == 9)
      return null;
    Record.DjLevel djl = Record.DjLevel.values()[b1[20] & 0x0F];
    Record.Clear clear = parseClear(b1[18], b1[21]);
    int exScore = ((b1[15] & 0xFF) << 8) + (b1[14] & 0xFF);
    int maxCombo = ((b1[17] & 0xFF) << 8) + (b1[16] & 0xFF);
    int missCount = ((b1[25] & 0xFF) << 8) + (b1[24] & 0xFF);
    int playCount = ((b2[1] & 0xFF) << 8) + (b2[0] & 0xFF);
    int just = ((b1[1] & 0xFF) << 8) + (b1[0] & 0xFF);
    int great = ((b1[3] & 0xFF) << 8) + (b1[2] & 0xFF);
    int good = ((b1[5] & 0xFF) << 8) + (b1[4] & 0xFF);
    int bad = ((b1[7] & 0xFF) << 8) + (b1[6] & 0xFF);
    int poor = ((b1[9] & 0xFF) << 8) + (b1[8] & 0xFF);
    return new Record(song, m, djl, clear, exScore, just, great, good, bad,
        poor, maxCombo, missCount, playCount);
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
