package name.lemonedo.iidx.record;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author LEE Chungmin
 */
class SongListReader {

  // prevent instantiation
  private SongListReader() {}

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

  private static Song parseSong(String[] args) {
    return parseSong(args, null);
  }

  private static Song parseSong(String[] args, Song another) {
    return Song.newBuilder().artist(args[2]).title(args[1]).genre(args[0])
        .bpm(args[3]).oldSong(args[16].equals("yes")).another(another)
        .difficulty(Difficulty.newInstance(args[4]), PlayMode.SN)
        .difficulty(Difficulty.newInstance(args[6]), PlayMode.SH)
        .difficulty(Difficulty.newInstance(args[8]), PlayMode.SA)
        .difficulty(Difficulty.newInstance(args[10]), PlayMode.DN)
        .difficulty(Difficulty.newInstance(args[12]), PlayMode.DH)
        .difficulty(Difficulty.newInstance(args[14]), PlayMode.DA)
        .numNotes(CommonValue.newInstance(args[5]), PlayMode.SN)
        .numNotes(CommonValue.newInstance(args[7]), PlayMode.SH)
        .numNotes(CommonValue.newInstance(args[9]), PlayMode.SA)
        .numNotes(CommonValue.newInstance(args[11]), PlayMode.DN)
        .numNotes(CommonValue.newInstance(args[13]), PlayMode.DH)
        .numNotes(CommonValue.newInstance(args[15]), PlayMode.DA).build();
  }
}
