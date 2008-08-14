package name.lemonedo.iidx;

import java.io.File;

public class RecordReaderFactory {

    public static RecordReader create(Version version, File saveFile) {
        switch (version) {
        case DISTORTED:
            return new RecordReaderDistorted(saveFile);
        case GOLD:
            return new RecordReaderGold(saveFile);
        default:
            return null;
        }
    }
}
