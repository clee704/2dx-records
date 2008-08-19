package name.lemonedo.iidx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author LEE Chungmin
 */
class SongListReader {

  static List<Song> read(String name) {
    return read(SongListReader.class.getResourceAsStream("songlist/" + name),
        "utf-8");
  }

  static List<Song> read(InputStream source, String charsetName) {
    Scanner sc = new Scanner(source, charsetName);

    if (sc.hasNextLine())
      sc.nextLine(); // discard the first line (title row)

    List<Song> songList = new ArrayList<Song>();
    while (sc.hasNextLine()) {
      String[] args = sc.nextLine().split("\\t+");
      Song another = null;
      if (args.length == 18)
        another = parseSong(sc.nextLine().split("\\t+"));
      songList.add(parseSong(args, another));
    }
    sc.close();

    return songList;
  }

  private static int parseInt(String n) {
    if (!n.matches("\\d+"))
      return -1;
    else
      return Integer.parseInt(n);
  }

  private static Song parseSong(String[] args) {
    return parseSong(args, null);
  }

  private static Song parseSong(String[] args, Song another) {
    return Song.newBuilder().artist(args[2]).title(args[1]).genre(args[0])
        .bpm(args[3]).oldSong(args[16].equals("yes")).another(another)
        .difficulty(parseInt(args[4]), PlayMode.SN)
        .difficulty(parseInt(args[6]), PlayMode.SH)
        .difficulty(parseInt(args[8]), PlayMode.SA)
        .difficulty(parseInt(args[10]), PlayMode.DN)
        .difficulty(parseInt(args[12]), PlayMode.DH)
        .difficulty(parseInt(args[14]), PlayMode.DA)
        .numNotes(parseInt(args[5]), PlayMode.SN)
        .numNotes(parseInt(args[7]), PlayMode.SH)
        .numNotes(parseInt(args[9]), PlayMode.SA)
        .numNotes(parseInt(args[11]), PlayMode.DN)
        .numNotes(parseInt(args[13]), PlayMode.DH)
        .numNotes(parseInt(args[15]), PlayMode.DA).build();
  }
}
