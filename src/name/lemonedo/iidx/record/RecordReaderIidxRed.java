package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

/**
 * @author LEE Chungmin
 *
 */
class RecordReaderIidxRed extends AbstractRecordReader {

  RecordReaderIidxRed(File saveFile) throws IOException {
    super(Version.IIDX_RED, saveFile, "iidxred.txt",
        new MetaInfo(115090, 26), new MetaInfo(128974, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, getVersion(), song, mode);
  }
}
