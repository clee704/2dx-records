package name.lemonedo.iidx.record;

import java.io.File;
import java.io.IOException;

import name.lemonedo.util.Pair;

/**
 * @author LEE Chungmin
 *
 */
public class RecordReaderIidxRed extends AbstractRecordReader {

  RecordReaderIidxRed(File saveFile) throws IOException {
    super(Version.IIDX_RED, saveFile, "iidxred.txt",
        new Pair<Integer>(115090, 26), new Pair<Integer>(128974, 2));
  }

  @Override
  protected Record parseRecord(byte[][] b, Song song, PlayMode mode) {
    return RecordReaderHelper.parseRecord(b, song, mode);
  }
}
