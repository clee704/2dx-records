package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

import name.lemonedo.util.Pair;

/**
 * 
 * @author LEE Chungmin
 */
class RecordReaderDistorted extends AbstractRecordReader {

  RecordReaderDistorted(File saveFile) throws IOException {
    super(Version.DISTORTED, saveFile, "distorted.txt",
        new Pair<Integer>(117686, 26), new Pair<Integer>(132662, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, song, mode);
  }
}
