package name.lemonedo.iidx.record;

import static name.lemonedo.iidx.record.Record.DjLevel.*;
import static name.lemonedo.iidx.record.RecordReaderTests.*;
import static org.junit.Assert.*;

import java.util.*;

import name.lemonedo.util.Algorithms;

import org.junit.Test;

public class RecordReaderGoldTest {

  @Test
  public void testRecordReaderGold() {
    RecordReader recordReader = createRecordReader("gold.psu");
    assertTrue(recordReader != null);
    assertTrue(recordReader.getVersion() == Version.GOLD);
  }

  @Test
  public void testRead() {
    RecordReader recordReader = createRecordReader("gold.psu");
    List<Record> records1 = recordReader.read(PlayMode.DH);
    List<Record> records2 = recordReader.read(PlayMode.DA);

    Record r1 = Algorithms.findIf(records2, new MatchTitle("Drivin'"));
    assertRecordEquals(AA, 1243, 522, 199, 63, 5, 21, 256, 26, 2, r1);
    Record r2 = Algorithms.findIf(records1, new MatchTitle("op.31 叙情"));
    assertRecordEquals(B, 876, 346, 184, 170, 8, 10, 230, 14, 20, r2);
    Record r3 = Algorithms.findIf(records1, new MatchTitle("QQQ"));
    assertRecordEquals(A, 1885, 698, 489, 99, 41, 76, 196, 86, 10, r3);
  }
}
