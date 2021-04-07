package com.unicamp.mc322.lab02.song;

public class Song implements Comparable<Song> {
  private String name;
  private String artist;
  private SongGenre genre;
  private int length;

  public Song(String name, String genre, String artist, int length) {
    this.name = name;
    this.artist = artist;
    setGenre(genre);
    this.length = length;
  }

  public String getDisplayName() {
    return getArtist() + " - " + getName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public SongGenre getGenre() {
    return genre;
  }

  public void setGenre(SongGenre genre) {
    this.genre = genre;
  }

  public void setGenre(String genre) {
    this.genre = SongGenre.valueOf(genre.toUpperCase());
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  // Essa função faz parte do implements Comparable, que permite utilizarmos
  // o utilitário de sorting do Collections definindo que as comparações devem
  // ser feitas usando os nomes das músicas.
  @Override
  public int compareTo(Song s) {
    return name.compareTo(s.getName());
  }
}
