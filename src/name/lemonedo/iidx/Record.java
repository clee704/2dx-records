package name.lemonedo.iidx;

import java.util.Comparator;

public class Record {

    public static enum DjLevel {
        AAA, AA, A, B, C, D, E, F
    }

    public static enum Clear {

        NO_PLAY,
        NO_CLEAR,
        ASSIST_CLEAR,
        EASY_CLEAR,
        CLEAR,
        HARD_CLEAR,
        FULL_COMBO,
        PERFECT,
        FAILED;

        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    private static class CommonValue extends Value {

        public CommonValue(int value) {
            super(value, 0, 9999);
        }
    }

    public static final Comparator<Record> TITLE_ORDER;
    public static final Comparator<Record> DIFFICULTY_ORDER;

    static {
        TITLE_ORDER = new Comparator<Record>() {
            public int compare(Record o1, Record o2) {
                String t1 = o1.getSong().getTitle();
                String t2 = o2.getSong().getTitle();
                return t1.compareToIgnoreCase(t2);
            };
        };
        DIFFICULTY_ORDER = new Comparator<Record>() {
            public int compare(Record o1, Record o2) {
                Value d1 = o1.getDifficulty();
                Value d2 = o2.getDifficulty();
                if (d1 == null)
                    return -1;
                else if (d2 == null)
                    return 1;
                else
                    return d1.toInt() - d2.toInt();
            };
        };
    }

    private final Song song;
    private final Value difficulty;
    private final Value totalNotes;
    private final PlayMode playMode;
    private final DjLevel djLevel;
    private final Clear clear;

    private final Value exScore;
    private final Value just;
    private final Value great;
    private final Value good;
    private final Value bad;
    private final Value poor;

    private final Value maxCombo;
    private final Value missCount;
    private final Value playCount;

    public Record(Song song, PlayMode playMode, DjLevel djLevel, Clear clear,
                  int exScore, int just, int great, int good, int bad, int poor,
                  int maxCombo, int missCount, int playCount) {
        if (song.hasAnotherSong() && (playMode == PlayMode.SA
                || playMode == PlayMode.DA))
            song = song.getAnotherSong();
        this.song = song;
        this.difficulty = song.getDifficulty(playMode);
        this.totalNotes = song.getNumNotes(playMode);
        this.playMode = playMode;
        this.djLevel = djLevel;
        this.clear = clear;
        this.exScore = new CommonValue(exScore);
        this.just = new CommonValue(just);
        this.great = new CommonValue(great);
        this.good = new CommonValue(good);
        this.bad = new CommonValue(bad);
        this.poor = new CommonValue(poor);
        this.maxCombo = new CommonValue(maxCombo);
        this.missCount = new CommonValue(missCount);
        this.playCount = new CommonValue(playCount);
    }

    public Song getSong() {
        return song;
    }

    public Value getDifficulty() {
        return difficulty;
    }

    public Value getTotalNotes() {
        return totalNotes;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public DjLevel getDjLevel() {
        return djLevel;
    }

    public Clear getClear() {
        return clear;
    }

    public Value getExScore() {
        return exScore;
    }

    public double getScoreRate() {
        if (exScore.isDefined() && totalNotes.isDefined())
            return ((double) exScore.toInt() / (totalNotes.toInt() * 2)) * 100;
        else
            return Double.NaN;
    }

    public Value getJust() {
        return just;
    }

    public Value getGreat() {
        return great;
    }

    public Value getGood() {
        return good;
    }

    public Value getBad() {
        return bad;
    }

    public Value getPoor() {
        return poor;
    }

    public Value getMaxCombo() {
        return maxCombo;
    }

    public Value getMissCount() {
        return missCount;
    }

    public Value getPlayCount() {
        return playCount;
    }

    private static final String TO_STRING_FORMAT = "[SONG TITLE: %s, PLAY MOD" +
                "E: %s, DJ LEVEL: %s, CLEAR: %s, EX SCORE: %s, MAX COMBO: %s," +
                " MISS COUNT: %s, PLAY COUNT: %s, TOTAL NOTES: %s, JUST: %s, " +
                "GREAT: %s, GOOD: %s, BAD: %s, POOR: %s";

    public String toString() {
        return String.format(TO_STRING_FORMAT, song.getTitle(), playMode,
                djLevel, clear, exScore, maxCombo, missCount, playCount,
                totalNotes, just, great, good, bad, poor);
    }
}
