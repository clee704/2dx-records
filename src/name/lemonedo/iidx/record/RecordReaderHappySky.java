package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author LEE Chungmin
 */
class RecordReaderHappySky extends AbstractRecordReader {

  RecordReaderHappySky(File saveFile) throws IOException {
    super(Version.HAPPY_SKY, saveFile, "happysky.txt",
        new MetaInfo(114066, 26), new MetaInfo(128418, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, getVersion(), song, mode);
  }
}
