package name.lemonedo.iidx;

import java.util.Collection;

public interface RecordReader {

  public Version getVersion();
  public Collection<Record> read(PlayMode mode);
}
