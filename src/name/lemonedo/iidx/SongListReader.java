package name.lemonedo.iidx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongListReader {

  public static List<Song> read(String path) {
    return read(SongListReader.class.getResourceAsStream(path));
  }

  public static List<Song> read(InputStream in) {
    Scanner sc = new Scanner(in, "utf-8");

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
      return Integer.MAX_VALUE;
    else
      return Integer.parseInt(n);
  }

  private static Song parseSong(String[] args) {
    return parseSong(args, null);
  }

  private static Song parseSong(String[] args, Song another) {
    return new Song(args[0], args[1], args[2], args[16].equals("yes"),
        parseInt(args[4]), parseInt(args[6]), parseInt(args[8]),
        parseInt(args[10]), parseInt(args[12]), parseInt(args[14]),
        parseInt(args[5]), parseInt(args[7]), parseInt(args[9]),
        parseInt(args[11]), parseInt(args[13]), parseInt(args[15]), another);
  }
}
