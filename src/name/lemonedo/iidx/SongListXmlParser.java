package name.lemonedo.iidx;

import static name.lemonedo.iidx.PlayMode.DA;
import static name.lemonedo.iidx.PlayMode.DH;
import static name.lemonedo.iidx.PlayMode.DN;
import static name.lemonedo.iidx.PlayMode.SA;
import static name.lemonedo.iidx.PlayMode.SH;
import static name.lemonedo.iidx.PlayMode.SN;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SongListXmlParser {

  public static List<Song> parse(String path) {
    InputStream in = null;
    try {
      try {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = fac.newDocumentBuilder();
        in = SongListXmlParser.class.getResourceAsStream(path);
        Document d = builder.parse(in);
        NodeList nodeList = d.getElementsByTagName("song");
        List<Song> songList = new ArrayList<Song>();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
          songList.add(parse(nodeList.item(i), d));
        return songList;
      } finally {
        if (in != null)
          in.close();
      }
    } catch (Exception e) {
      throw new RuntimeException("parse error", e);
    } 
  }

  private static Song parse(Node node, Document doc) {
    Song.Builder builder = Song.newBuilder();

    NamedNodeMap attrs = node.getAttributes();
    builder.id(parseInt(attrs.getNamedItem("id").getNodeValue().substring(1)));
    builder.oldSong(attrs.getNamedItem("age").getNodeValue().equals("old"));

    NodeList childs = node.getChildNodes();
    builder.artist(childs.item(0).getTextContent());
    builder.title(childs.item(1).getTextContent());
    builder.genre(childs.item(2).getTextContent());
    builder.bpm(childs.item(3).getTextContent());

    NodeList s = childs.item(4).getFirstChild().getChildNodes();
    NodeList d = childs.item(4).getLastChild().getChildNodes();
    for (int i = 0; i < s.getLength(); i++) {
      Node n = s.item(i);
      String name = n.getNodeName();
      if (name.equals("normal")) {
        builder.difficulty(parseInt(n.getFirstChild().getTextContent()), SN);
        builder.numNotes(parseInt(n.getLastChild().getTextContent()), SN);
      } else if (name.equals("hyper")) {
        builder.difficulty(parseInt(n.getFirstChild().getTextContent()), SH);
        builder.numNotes(parseInt(n.getLastChild().getTextContent()), SH);
      } else if (name.equals("another")) {
        builder.difficulty(parseInt(n.getFirstChild().getTextContent()), SA);
        builder.numNotes(parseInt(n.getLastChild().getTextContent()), SA);
      }
    }
    if (s != d)
      for (int i = 0; i < d.getLength(); i++) {
        Node n = d.item(i);
        String name = n.getNodeName();
        if (name.equals("normal")) {
          builder.difficulty(parseInt(n.getFirstChild().getTextContent()), DN);
          builder.numNotes(parseInt(n.getLastChild().getTextContent()), DN);
        } else if (name.equals("hyper")) {
          builder.difficulty(parseInt(n.getFirstChild().getTextContent()), DH);
          builder.numNotes(parseInt(n.getLastChild().getTextContent()), DH);
        } else if (name.equals("another")) {
          builder.difficulty(parseInt(n.getFirstChild().getTextContent()), DA);
          builder.numNotes(parseInt(n.getLastChild().getTextContent()), DA);
        }
      }

    Node anotherId = attrs.getNamedItem("another_id");
    if (anotherId != null)
      builder.another(parse(doc.getElementById(anotherId.getTextContent()),
          doc));
    return builder.build();
  }

  private static int parseInt(String s) {
    if (s.matches("\\d+"))
      return Integer.parseInt(s);
    else
      return 0;
  }
}
