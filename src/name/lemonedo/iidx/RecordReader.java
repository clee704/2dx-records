package name.lemonedo.iidx;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class RecordReader {

    protected final File saveFile;

    public RecordReader(File saveFile) {
        this.saveFile = saveFile;
    }

    public abstract List<Record> read(PlayMode playMode) throws IOException;
}
