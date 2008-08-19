package name.lemonedo.iidx.record.app;

import java.io.IOException;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author LEE Chungmin
 */
class UpdateChecker {

  private static final String VERSION = "0.20r28";
  private static final String UPDATE_CHECK_URL =
      "http://code.google.com/p/2dx-records/wiki/LastestVersionCheck"; 

  private static final String REGEXP_1 =
      ".*<div[^<>]+id\\s*=\\s*[\"']?wikicontent[\"']?[^<>]*>.*";
  private static final String REGEXP_2 =
      "[^<>]*<p[^<>]*>" + VERSION + " .*";

  // prevent instantiation
  private UpdateChecker() {}

  static boolean check(int timeout) throws IOException {
    Scanner sc = null;
    try {
      URLConnection conn = new URL(UPDATE_CHECK_URL).openConnection();
      conn.setReadTimeout(timeout);
      sc = new Scanner(conn.getInputStream(), "UTF-8");
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (line.contains("wikicontent") && line.matches(REGEXP_1)) {
          line = sc.nextLine();
          if (line != null && line.matches(REGEXP_2))
            return true;
          else
            return false;
        }
      }
      return false;
    } finally {
      if (sc != null)
        sc.close();
    }
  }
}
