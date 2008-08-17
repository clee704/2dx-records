package name.lemonedo.iidx;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SongListXmlWriter {

  public static void main(String[] args) throws Exception {
    String[] names = {"happysky", "distorted", "gold"};
    int[] baseIds = {12000, 13000, 14000};
    String dir = "D:/dev/2dx-records/trunk/src/name/lemonedo/iidx/songlist/";
    for (int i = 0; i < 3; i++)
      write(SongListXmlParser.parse("songlist/" + names[i] + ".xml"), dir +
          names[i] + ".xml", baseIds[i]);
  }

  public static void write(List<Song> songList, String path, int baseId)
  throws IOException {
    String originalLineSeparator = System.getProperty("line.separator");
    PrintStream out = null;
    try {
      System.setProperty("line.separator", "\n");
      out = new PrintStream(path, "UTF-8");
      out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      out.println("<!DOCTYPE songlist [");
      writeDtd(out);
      out.println("]>");
      out.println("<songlist>");
      List<Song> anotherList = new ArrayList<Song>();
      List<Integer> originalList = new ArrayList<Integer>();
      for (Song s : songList) {
        if (s.getTitle().equals("-"))
          continue;
        out.printf("<song id=\"N%d\"", s.getId());
        if (s.isOldSong())
          out.print(" age=\"old\"");
        if (!s.hasAnotherSong()) {
          out.print(">\n");
        } else {
          out.printf(" another_id=\"N%d\">\n", s.getAnotherSong().getId());
          anotherList.add(s.getAnotherSong());
          originalList.add(s.getId());
        }
        writeSong(out, s);
        out.println("</song>");
      }
      for (int i = 0; i < anotherList.size(); i++) {
        Song s = anotherList.get(i);
        int originalId = originalList.get(i);
        out.printf("<another_song id=\"N%d\"", s.getId());
        if (s.isOldSong())
          out.print(" age=\"old\"");
        out.printf(" original_id=\"N%d\">\n", originalId);
        writeSong(out, s);
        out.println("</another_song>");
      }
      out.println("</songlist>");
    } finally {
      System.setProperty("line.separator", originalLineSeparator);
      if (out != null)
        out.close();
    }
  }

  private static void writeDtd(PrintStream out) {
    out.println("  <!ELEMENT songlist (song, (song|another_song)*)>");
    out.println("  <!ELEMENT song (artist, title, genre, bpm, modes)>");
    out.println("  <!ELEMENT artist (#PCDATA)>");
    out.println("  <!ELEMENT title (#PCDATA)>");
    out.println("  <!ELEMENT genre (#PCDATA)>");
    out.println("  <!ELEMENT bpm (#PCDATA)>");
    out.println("  <!ELEMENT modes (single, double)>");
    out.println("  <!ELEMENT single (normal?, hyper?, another?)>");
    out.println("  <!ELEMENT double (normal?, hyper?, another?)>");
    out.println("  <!ELEMENT normal (diff, notes)>");
    out.println("  <!ELEMENT hyper (diff, notes)>");
    out.println("  <!ELEMENT another (diff, notes)>");
    out.println("  <!ELEMENT diff (#PCDATA)>");
    out.println("  <!ELEMENT notes (#PCDATA)>");    
    out.println("  <!ATTLIST song id ID #REQUIRED>");
    out.println("  <!ATTLIST song age (old|new) \"new\">");
    out.println("  <!ATTLIST song another_id IDREF #IMPLIED>");
    out.println("  <!ELEMENT another_song (artist, title, genre, bpm, modes)>");
    out.println("  <!ATTLIST another_song id ID #REQUIRED>");
    out.println("  <!ATTLIST another_song age (old|new) \"new\">");
    out.println("  <!ATTLIST another_song original_id IDREF #REQUIRED>");
  }

  private static void writeSong(PrintStream out, Song s) {
    out.println("  <artist>" + validate(s.getArtist()) + "</artist>");
    out.println("  <title>" + validate(s.getTitle()) + "</title>");
    out.println("  <genre>" + validate(s.getGenre()) + "</genre>");
    out.println("  <bpm>" + s.getBpm() + "</bpm>");
    out.println("  <modes>");
    out.println("    <single>");
    for (int i = 0; i < 3; i++)
      writeMode(out, s, PlayMode.values()[i]);
    out.println("    </single>");
    out.println("    <double>");
    for (int i = 3; i < 6; i++)
      writeMode(out, s, PlayMode.values()[i]);
    out.println("    </double>");
    out.println("  </modes>");
  }

  private static void writeMode(PrintStream out, Song s, PlayMode mode) {
    String modeName;
    switch (mode) {
    case SN:
    case DN:
      modeName = "normal";
      break;
    case SH:
    case DH:
      modeName = "hyper";
      break;
    default:
      modeName = "another";
      break;
    }
    if (s.getNumNotes(mode).isDefined()) {
      out.print("      <" + modeName + ">");
      out.print("<diff>" + s.getDifficulty(mode) + "</diff>");
      out.print("<notes>" + s.getNumNotes(mode) + "</notes>");
      out.println("</" + modeName + ">");
    }
  }

  private static String validate(String s) {
    return s.replace("<", "&lt;").replace("&", "&amp;");
  }
}
