package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author LEE Chungmin
 */
class RecordReaderDistorted extends AbstractRecordReader {

  RecordReaderDistorted(File saveFile) throws IOException {
    super(Version.DISTORTED, saveFile, "distorted.txt",
        new MetaInfo(117686, 26), new MetaInfo(132662, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, getVersion(), song, mode);
  }
}
