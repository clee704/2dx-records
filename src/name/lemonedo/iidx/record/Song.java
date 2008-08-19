package name.lemonedo.iidx.record;

import java.util.EnumMap;

/**
 * 
 * @author LEE Chungmin
 */
public class Song {

  private static final String TO_STRING_FORMAT = "GENRE: %s, TITLE: %s, ARTIS" +
      "T: %s";

  private final String genre;
  private final String title;
  private final String artist;
  private final String bpm;
  private final boolean oldSong;

  private final EnumMap<PlayMode, Value> difficulties;
  private final EnumMap<PlayMode, Value> numNotes;

  private final Song another;

  private Song(String genre, String title, String artist, String bpm,
               boolean oldSong, EnumMap<PlayMode, Value> difficulties,
               EnumMap<PlayMode, Value> numNotes, Song another) {
    this.genre = genre;
    this.title = title;
    this.artist = artist;
    this.bpm = bpm;
    this.oldSong = oldSong;
    this.difficulties = difficulties.clone();
    this.numNotes = numNotes.clone();
    this.another = another;
  }

  public static Builder newBuilder() {
    return new Builder();
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

  public String getBpm() {
    return bpm;
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

  @Override
  public String toString() {
    return String.format(TO_STRING_FORMAT, genre, title, artist);
  }

  public static class Builder {

    private String genre;
    private String title;
    private String artist;
    private String bpm;
    private boolean oldSong;
    private EnumMap<PlayMode, Value> difficulties;
    private EnumMap<PlayMode, Value> numNotes;
    private Song another;

    private Builder() {
      difficulties = new EnumMap<PlayMode, Value>(PlayMode.class);
      numNotes = new EnumMap<PlayMode, Value>(PlayMode.class);
      for (PlayMode m : PlayMode.values()) {
        difficulties.put(m, Value.UNDEFINED);
        numNotes.put(m, Value.UNDEFINED);
      }
    }

    public Song build() {
      if (genre == null || title == null || artist == null || bpm == null)
        throw new IllegalStateException();
      return new Song(genre, title, artist, bpm, oldSong, difficulties,
          numNotes, another);
    }

    public Builder genre(String val) {
      genre = val;
      return this;
    }

    public Builder title(String val) {
      title = val;
      return this;
    }

    public Builder artist(String val) {
      artist = val;
      return this;
    }

    public Builder bpm(String val) {
      bpm = val;
      return this;
    }

    public Builder oldSong(boolean val) {
      oldSong = val;
      return this;
    }

    public Builder difficulty(int val, PlayMode m) {
      difficulties.put(m, Value.create(val, 1, 12));
      return this;
    }

    public Builder numNotes(int val, PlayMode m) {
      numNotes.put(m, Value.create(val, 1, 9999));
      return this;
    }

    public Builder another(Song val) {
      another = val;
      return this;
    }
  }
}
