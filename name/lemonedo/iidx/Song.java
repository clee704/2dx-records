package name.lemonedo.iidx;

import java.util.EnumMap;

public class Song {

    private static final String TO_STRING_FORMAT = "GENRE: %s, TITLE: %s, ART" +
            "IST: %s";

    private final String genre;
    private final String title;
    private final String artist;
    private final boolean oldSong;

    private final EnumMap<PlayMode, Value> difficulties;
    private final EnumMap<PlayMode, Value> numNotes;

    private final Song another;

    public Song(String genre, String title, String artist, boolean oldSong,
                int difficultySn, int difficultySh, int difficultySa,
                int difficultyDn, int difficultyDh, int difficultyDa,
                int numNotesSn, int numNotesSh, int numNotesSa, int numNotesDn,
                int numNotesDh, int numNotesDa) {
        this(genre, title, artist, oldSong, difficultySn, difficultySh,
                difficultySa, difficultyDn, difficultyDh, difficultyDa,
                numNotesSn, numNotesSh, numNotesSa, numNotesDn, numNotesDh,
                numNotesDa, null);
    }

    public Song(String genre, String title, String artist, boolean oldSong,
                int difficultySn, int difficultySh, int difficultySa,
                int difficultyDn, int difficultyDh, int difficultyDa,
                int numNotesSn, int numNotesSh, int numNotesSa, int numNotesDn,
                int numNotesDh, int numNotesDa, Song another) {
        this.genre = genre;
        this.title = title;
        this.artist = artist;
        this.oldSong = oldSong;
        this.another = another;
        difficulties = new EnumMap<PlayMode, Value>(PlayMode.class);
        difficulties.put(PlayMode.SN, new Value(difficultySn, 1, 12));
        difficulties.put(PlayMode.SH, new Value(difficultySh, 1, 12));
        difficulties.put(PlayMode.SA, new Value(difficultySa, 1, 12));
        difficulties.put(PlayMode.DN, new Value(difficultyDn, 1, 12));
        difficulties.put(PlayMode.DH, new Value(difficultyDh, 1, 12));
        difficulties.put(PlayMode.DA, new Value(difficultyDa, 1, 12));
        numNotes = new EnumMap<PlayMode, Value>(PlayMode.class);
        numNotes.put(PlayMode.SN, new Value(numNotesSn, 0, 9999));
        numNotes.put(PlayMode.SH, new Value(numNotesSh, 0, 9999));
        numNotes.put(PlayMode.SA, new Value(numNotesSa, 0, 9999));
        numNotes.put(PlayMode.DN, new Value(numNotesDn, 0, 9999));
        numNotes.put(PlayMode.DH, new Value(numNotesDh, 0, 9999));
        numNotes.put(PlayMode.DA, new Value(numNotesDa, 0, 9999));
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isOldSong() {
        return oldSong;
    }

    public boolean hasAnotherSong() {
        return another != null;
    }

    public Song getAnotherSong() {
        return another;
    }

    public Value getNumNotes(PlayMode playMode) {
        return numNotes.get(playMode);
    }

    public Value getDifficulty(PlayMode playMode) {
        return difficulties.get(playMode);
    }

    public String toString() {
        return String.format(TO_STRING_FORMAT, genre, title, artist);
    }
}
