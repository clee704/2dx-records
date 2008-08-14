package name.lemonedo.iidx.util;

import static name.lemonedo.iidx.PlayMode.DA;
import static name.lemonedo.iidx.PlayMode.SA;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import name.lemonedo.util.Method;

public class HtmlPrinter {

    private final String ln;
    private final String bodyLayout;
    private final String tableLayout;
    private final String entryLayout;

    private final Map<String, List<Record>> labeledRecords;
    private Map<String, List<Record>> toPrint;
    private boolean excludeNoClear;

    public HtmlPrinter() {
        try {
            ln = System.getProperty("line.separator");
            bodyLayout = loadLayoutFile(getClass().getResourceAsStream(
                    "layout/layout_body.txt"));
            tableLayout = loadLayoutFile(getClass().getResourceAsStream(
                    "layout/layout_table.txt"));
            entryLayout = loadLayoutFile(getClass().getResourceAsStream(
                    "layout/layout_entry.txt"));
            labeledRecords = new LinkedHashMap<String, List<Record>>();
            toPrint = null;
            excludeNoClear = false;
        } catch (Exception e) {
            throw new RuntimeException("can't open layout files", e);
        }
    }

    public boolean addRecords(String label, List<Record> records) {
        if (label.length() == 0)
            throw new IllegalArgumentException("empty label");
        if (records.size() != 0) {
            labeledRecords.put(label, new ArrayList<Record>(records));
            return true;
        } else
            return false;
    }

    public void setExcludeNoClear(boolean aFlag) {
        excludeNoClear = aFlag;
    }

    public void print(Version v) {
        getToPrint();
        if (toPrint.isEmpty())
            throw new NoRecordsToPrintException();
        Formatter f = new Formatter();
        StringBuilder buf1 = new StringBuilder();
        StringBuilder buf2 = new StringBuilder();
        printTables(f);
        printScripts1(buf1);
        printScripts2(buf2);
        String[] colorHex = toColorHex(v);
        System.out.println(String.format(bodyLayout, v, f, buf1, buf2,
                colorHex[0], colorHex[1]));
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
                List<Record> found = Algorithms.findIf(orig, new Cleared());
                if (!found.isEmpty())
                    toPrint.put(label, found);
            }
        } else {
            toPrint = labeledRecords;
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
                entries.format(entryLayout, oldSong ? "old" : "new", title,
                        playMode, r.getDifficulty(), toImgTag(r.getDjLevel()),
                        r.getExScore(), r.getScoreRate(), r.getTotalNotes(),
                        r.getJust(), r.getGreat(), r.getGood(), r.getBad(),
                        r.getPoor(), toImgTag(r.getClear()), r.getMaxCombo(),
                        r.getMissCount(), r.getPlayCount(),
                        (int) (1.98 * r.getScoreRate()));
            }
            f.format(tableLayout, label, entries);
        }
    }

    private void printScripts1(StringBuilder buf) {
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

    private void printScripts2(StringBuilder buf) {
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
        return String.format("'%s', %s, '%s', %s, '%s', %s, %.02f, %s, %s, " +
                      "%s, %s, %s, %s, '%s', %s, %s, %s",
                      s.getTitle().replaceAll("'", "\\\\'"),
                      s.isOldSong(), m, r.getDifficulty(), r.getDjLevel(),
                      r.getExScore(), r.getScoreRate(), r.getTotalNotes(),
                      r.getJust(), r.getGreat(), r.getGood(), r.getBad(),
                      r.getPoor(), toString(r.getClear()), r.getMaxCombo(),
                      r.getMissCount(), r.getPlayCount())
                      .replaceAll(", -,", ", '-',");
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
        case PERFECT:
            return "PERFECT";
        default:
            return null;
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
            return null;
        }
    }

    private String[] toColorHex(Version v) {
        switch (v) {
        case DISTORTED:
            return new String[] {"333333", "666666"};
        case GOLD:
            return new String[] {"403511", "997e28"};
        default:
            return null;
        }
    }

    private static class Cleared implements Method<Boolean, Record> {
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
