package name.lemonedo.iidx;

import java.io.*;
import java.util.*;

class RecordReaderGold extends RecordReader {

    private static final int[] bits = {0x00, 0x01, 0x03, 0x07, 0x0F, 0x1F, 0x3F,
        0x7F, 0xFF};

    private final List<Song> songList;
    private final EnumMap<PlayMode, List<Record>> records;

    public RecordReaderGold(File saveFile) {
        super(saveFile);
        songList = loadSongList();
        records = new EnumMap<PlayMode, List<Record>>(PlayMode.class);
    }

    public List<Record> read(PlayMode playMode) throws IOException {
        synchronized (records) {
            if (records.isEmpty())
                read();
        }
        return records.get(playMode);
    }

    private List<Song> loadSongList() {
        try {
            return SongListReader.read(getClass().getResourceAsStream(
                    "songlist/gold.txt"));
        } catch (Exception e) {
            throw new RuntimeException("can't open a resource file", e);
        }
    }

    private void read() throws IOException {
        FileInputStream fis;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(saveFile);
            bis = new BufferedInputStream(fis);
            for (int i = 0; i < 99480; i++)
                bis.read();
            for (PlayMode m : PlayMode.values()) {
                records.put(m, new ArrayList<Record>());
                readHelper(bis, m);
            }
        } catch (FileNotFoundException e) {
            records.clear();
            throw new IOException("can't find " + saveFile, e);
        } catch (Exception e) {
            records.clear();
            throw new IOException("file is corrupt", e);
        } finally {
            if (bis != null)
                bis.close();
        }
    }

    private void readHelper(BufferedInputStream bis, PlayMode m)
    throws IOException {
        byte[] buf = new byte[20];
        for (Song s : songList) {
            bis.read(buf);
            Record r = parseRecord(buf, s, m);
            if (r != null)
                records.get(m).add(r);
        }
    }

    private Record parseRecord(byte[] b, Song s, PlayMode m) {
        if ((b[13] & 0xFF) == 0x90)
            return null;
        Record.DjLevel djl = Record.DjLevel.values()[down(b[13], 4, 4)];
        Record.Clear clear = Record.Clear.values()[down(b[17], 3, 5)];
        int exScore = up(b[11], 6, 8) + up(b[10], 8, 0);
        int maxCombo = up(b[13], 4, 10) + up(b[12], 8, 2) + down(b[11], 2, 6);
        int missCount = up(b[15], 6, 8) + up(b[14], 8, 0);
        int playCount = up(b[17], 5, 9) + up(b[16], 8, 1) + down(b[15], 1, 7);
        int just = up(b[3], 6, 8) + up(b[2], 8, 0);
        int great = up(b[5], 4, 10) + up(b[4], 8, 2) + down(b[3], 2, 6);
        int good = up(b[7], 6, 8) + up(b[6], 8, 0);
        int bad = up(b[9], 4, 10) + up(b[8], 8, 2) + down(b[7], 2, 6);
        int poor = up(b[19], 6, 8) + up(b[18], 8, 0);
        return new Record(s, m, djl, clear, exScore, just, great, good, bad,
                poor, maxCombo, missCount, playCount);
    }

    private int up(byte b, int numBits, int shift) {
        if (numBits >= 0 && numBits <= 8)
            return (b & bits[numBits]) << shift;
        else
            throw new IllegalArgumentException("out of range");
    }

    private int down(byte b, int numBits, int shift) {
        if (numBits >= 0 && numBits <= 8)
            return (b >>> shift) & bits[numBits];
        else
            throw new IllegalArgumentException("out of range");
    }
}
