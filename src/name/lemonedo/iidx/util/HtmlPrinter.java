package name.lemonedo.iidx.util;

import static name.lemonedo.iidx.PlayMode.DA;
import static name.lemonedo.iidx.PlayMode.SA;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import name.lemonedo.iidx.PlayMode;
import name.lemonedo.iidx.Record;
import name.lemonedo.iidx.Song;
import name.lemonedo.iidx.Version;
import name.lemonedo.util.Algorithms;
import name.lemonedo.util.UnaryPredicate;

/**
 * 
 * @author LEE Chungmin
 */
public class HtmlPrinter {

  private final String ln;
  private final String bodyLayout;
  private final String tableLayout;
  private final String entryLayout;

  private final Map<String, List<Record>> labeledRecords;
  private Map<String, List<Record>> toPrint;
  private boolean sortSEPARATEly;
  private boolean excludeNoClear;

  /**
   * Constructs a new <code>HtmlPrinter</code>.
   */
  public HtmlPrinter() {
    try {
      ln = System.getProperty("line.separator");
      bodyLayout = loadLayoutFile(resourceAsStream("layout/layout_body.txt"));
      tableLayout = loadLayoutFile(resourceAsStream("layout/layout_table.txt"));
      entryLayout = loadLayoutFile(resourceAsStream("layout/layout_entry.txt"));
      labeledRecords = new LinkedHashMap<String, List<Record>>();
      toPrint = null;
      sortSEPARATEly = false;
      excludeNoClear = false;
    } catch (Exception e) {
      // cannot be reached
      throw new RuntimeException("can't open layout files", e);
    }
  }

  /**
   * Adds the specified records to print to this printer, with the specified
   * label. With the label as a title, records with the same label will be
   * printed together in a table when printing. If the label or the record list
   * is empty, records will not be added.
   * 
   * @param label the label for the records
   * @param records the records
   * @return <code>true</code> if the records are added 
   * @throws NullPointerException if <code>label</code> or <code>records</code>
   *        is <code>null</code>
   */
  public boolean addRecords(String label, List<Record> records) {
    if (label == null || records == null)
      throw new NullPointerException();
    if (label.length() == 0 || records.size() == 0)
      return false;
    labeledRecords.put(label, new ArrayList<Record>(records));
    return true;
  }

  /**
   * Removes all records which were added to this.
   */
  public void clear() {
    labeledRecords.clear();
  }

  /**
   * Sets whether or not this sorts records sEPARATEly according to the play
   * mode while printing. Default setting is <code>false</code>. 
   * 
   * @param aFlag a flag
   */
  public void setSortSeparately(boolean aFlag) {
    sortSEPARATEly = aFlag;
  }

  /**
   * Sets whether this should not prints records which have 'No Clear' as its
   * clear state. Default is <code>false</code>.
   * @param aFlag <code>true</code> if this should exclude 'No Clear' on
   *       printing
   */
  public void setExcludeNoClear(boolean aFlag) {
    excludeNoClear = aFlag;
  }

  /**
   * Prints records with no specific version title (head). This method can be
   * used to print records from different versions, though it is not recommended
   * to print records from different versions in one document.
   * 
   * @param out <code>PrintStream</code> to which this prints
   * @return <code>false</code> if there is no records to print
   */
  public boolean print(PrintStream out) {
    return print(out, null);
  }

  /**
   * Prints records with the specified version as a title (head). If
   * <code>v</code> is <code>null</code>, then invoking this method is equal to
   * invoking {@link name.lemonedo.iidx.util.HtmlPrinter#print print()}.
   * 
   * @param out <code>PrintStream</code> to which this prints
   * @param v the version
   * @return <code>false</code> if there is no records to print
   */
  public boolean print(PrintStream out, Version v) {
    getToPrint();
    if (toPrint.isEmpty())
      return false;
    sortToPrint();
    Formatter f = new Formatter();
    StringBuilder buf1 = new StringBuilder();
    StringBuilder buf2 = new StringBuilder();
    printTables(f);
    printRecordLists(buf1);
    printLabelList(buf2);
    String[] colorHex = toColorHex(v);
    String title = (v == null ? "beatmaniaIIDX" : v.toString());
    out.println(String.format(bodyLayout, title, f, buf1, buf2,
        colorHex[0], colorHex[1]));
    return true;
  }

  private InputStream resourceAsStream(String name) {
    return getClass().getResourceAsStream(name);
  }

  private String loadLayoutFile(InputStream in) throws IOException {
    Scanner sc = new Scanner(in, "utf-8");
    try {
      StringBuilder buf = new StringBuilder();
      while (sc.hasNextLine())
        buf.append(sc.nextLine()).append(ln);
      return buf.toString();
    } finally {
      sc.close();
    }
  }

  private void getToPrint() {
    if (excludeNoClear) {
      toPrint = new LinkedHashMap<String, List<Record>>();
      for (String label : labeledRecords.keySet()) {
        List<Record> orig = labeledRecords.get(label);
        List<Record> found = Algorithms.filter(orig, new Cleared());
        if (!found.isEmpty())
          toPrint.put(label, found);
      }
    }
    else
      toPrint = labeledRecords;
  }

  private void sortToPrint() {
    for (List<Record> records : labeledRecords.values()) {
      Collections.sort(records, Record.TITLE_ORDER);
      Collections.sort(records, Record.DIFFICULTY_ORDER);
      if (sortSEPARATEly)
        Collections.sort(records, Record.PLAY_MODE_ORDER);
    }
  }

  private void printTables(Formatter f) {
    for (String label : toPrint.keySet()) {
      Formatter entries = new Formatter();
      for (Record r : toPrint.get(label)) {
        Song s = r.getSong();
        PlayMode playMode = r.getPlayMode();
        if (s.hasAnotherSong() && (playMode == SA || playMode == DA))
          s = s.getAnotherSong();
        String title = s.getTitle();
        boolean oldSong = s.isOldSong();
        if (title.contains("&"))
          title = title.replace("&", "&amp;");
        entries.format(entryLayout, oldSong ? "old" : "new", title, playMode, r
            .getDifficulty(), toImgTag(r.getDjLevel()), r.getExScore(), r
            .getScoreRate(), r.getTotalNotes(), r.getJust(), r.getGreat(), r
            .getGood(), r.getBad(), r.getPoor(), toImgTag(r.getClear()), r
            .getMaxCombo(), r.getMissCount(), r.getPlayCount(), (int) (1.98 * r
            .getScoreRate()));
      }
      f.format(tableLayout, label, entries);
    }
  }

  private void printRecordLists(StringBuilder buf) {
    int i = 0;
    for (String label : toPrint.keySet()) {
      i++;
      buf.append("    '").append(label).append("':[").append(ln);
      int j = 0;
      for (Record r : toPrint.get(label)) {
        j++;
        buf.append("        record(").append(toString(r)).append(')');
        if (j != toPrint.get(label).size())
          buf.append(',').append(ln);
      }
      buf.append(']');
      if (i != toPrint.size())
        buf.append(',').append(ln);
    }
  }

  private void printLabelList(StringBuilder buf) {
    int i = 0;
    for (String label : toPrint.keySet()) {
      i++;
      buf.append('\'').append(label).append('\'');
      if (i != toPrint.size())
        buf.append(", ");
    }
  }

  private String toString(Record r) {
    Song s = r.getSong();
    PlayMode m = r.getPlayMode();
    if (s.hasAnotherSong() && (m == PlayMode.SA || m == PlayMode.DA))
      s = s.getAnotherSong();
    return String.format(
        "'%s', %s, '%s', %s, '%s', %s, %.02f, %s, %s, "
            + "%s, %s, %s, %s, '%s', %s, %s, %s",
        s.getTitle().replaceAll("'", "\\\\'"), s.isOldSong(), m,
        r.getDifficulty(), r.getDjLevel(), r.getExScore(), r.getScoreRate(),
        r.getTotalNotes(), r.getJust(), r.getGreat(), r.getGood(), r.getBad(),
        r.getPoor(), toString(r.getClear()), r.getMaxCombo(), r.getMissCount(),
        r.getPlayCount()).replaceAll(", -,", ", '-',");
  }

  private String toString(Record.Clear clear) {
    switch (clear) {
    case NO_CLEAR:
      return "NO";
    case ASSIST_CLEAR:
      return "ASSIST";
    case EASY_CLEAR:
      return "EASY";
    case CLEAR:
      return "YES";
    case HARD_CLEAR:
      return "HARD";
    case FULL_COMBO:
      return "FC";
    default:
      return clear.toString();
    }
  }

  private String toImgTag(Record.DjLevel djLevel) {
    switch (djLevel) {
    case AAA:
      return "<img src=\"aaa.png\" alt=\"AAA\">";
    case AA:
      return "<img src=\"aa.png\" alt=\"AA\">";
    case A:
      return "<img src=\"a.png\" alt=\"A\">";
    default:
      return djLevel.toString();
    }
  }

  private String toImgTag(Record.Clear clear) {
    switch (clear) {
    case NO_CLEAR:
      return "NO";
    case ASSIST_CLEAR:
      return "ASSIST";
    case EASY_CLEAR:
      return "<img src=\"easy.gif\" alt=\"EASY\">";
    case CLEAR:
      return "YES";
    case HARD_CLEAR:
      return "<img src=\"hard.gif\" alt=\"HARD\">";
    case FULL_COMBO:
      return "<img src=\"fc.png\" alt=\"FC\">";
    case PERFECT:
      return "<img src=\"pf.png\" alt=\"PERFECT\">";
    default:
      return clear.toString();
    }
  }

  private String[] toColorHex(Version v) {
    if (v == null)
      return new String[] {"333333", "666666"};
    switch (v) {
    case HAPPY_SKY:
      return new String[] {"041640", "093499"};
    case GOLD:
      return new String[] {"403511", "997e28"};
    case DISTORTED:
    default:
      return new String[] {"333333", "666666"};
    }
  }

  private static class Cleared implements UnaryPredicate<Record> {

    public Boolean eval(Record e) {
      switch (e.getClear()) {
      case NO_PLAY:
      case NO_CLEAR:
        return false;
      default:
        return true;
      }
    }
  }
}
