package name.lemonedo.iidx.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import name.lemonedo.util.UnaryPredicate;

class RecordReaderTests {

  static RecordReader createRecordReader(String path) {
    try {
      File psuFile = new File(
          RecordReaderTests.class.getResource("resource/" + path).toURI());
      return new RecordReaderGold(psuFile);
    } catch (IOException e) {
      return null;
    } catch (URISyntaxException e) {
      return null;
    }
  }

  static void assertRecordEquals(Record.DjLevel djLevel, int exScore,
      int just, int great, int good, int bad, int poor, int maxCombo,
      int missCount, int playCount, Record r) {
    assertNotNull(r);
    assertEquals(djLevel, r.getDjLevel());
    assertEquals(exScore, r.getExScore().toInt());
    assertEquals(just, r.getJust().toInt());
    assertEquals(great, r.getGreat().toInt());
    assertEquals(good, r.getGood().toInt());
    assertEquals(bad, r.getBad().toInt());
    assertEquals(poor, r.getPoor().toInt());
    assertEquals(maxCombo, r.getMaxCombo().toInt());
    assertEquals(missCount, r.getMissCount().toInt());
    assertEquals(playCount, r.getPlayCount().toInt());
  }

  static class MatchTitle implements UnaryPredicate<Record> {

    private final String title;

    MatchTitle(String title) {
      this.title = title;
    }

    @Override
    public Boolean eval(Record object) {
      return title.equals(object.getSong().getTitle());
    }
  }
}
