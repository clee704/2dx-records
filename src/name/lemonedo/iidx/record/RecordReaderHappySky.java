package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

import name.lemonedo.util.Pair;

/**
 * 
 * @author LEE Chungmin
 */
class RecordReaderHappySky extends AbstractRecordReader {

  RecordReaderHappySky(File saveFile) throws IOException {
    super(Version.HAPPY_SKY, saveFile, "happysky.txt",
        new Pair<Integer>(114066, 26), new Pair<Integer>(128418, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, song, mode);
  }
}
