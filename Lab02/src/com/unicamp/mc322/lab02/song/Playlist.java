package com.unicamp.mc322.lab02.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.unicamp.mc322.lab02.user.User;

public class Playlist {
  private String name;
  private SongGenre genre;
  private User owner;
  private ArrayList<Song> songs;
  private Song currentSong;

  public Playlist(String name, String genre) {
    this.name = name;
    setGenre(genre);
    this.owner = null;
    this.songs = new ArrayList<Song>();
    this.currentSong = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public ArrayList<Song> getSongs() {
    return songs;
  }

  public int getSongsCount() {
    return songs.size();
  }

  public void addSong(Song song) {
    if (owner != null) {
      if (owner.isPremium() && songs.size() == 100) {
        throw new IllegalStateException("Operação não permitida: a playlist está cheia.");
      }
  
      if (!owner.isPremium() && songs.size() == 10) {
        throw new IllegalStateException("Operação não permitida: a playlist está cheia. Assine agora e adicione até 100 músicas na sua playlist!");
      }
    }
  
    if (song.getGenre() != genre) {
      throw new IllegalStateException("Operação não permitida: essa música não é do gênero dessa playlist.");
    }

    if (songs.contains(song)) {
      return;
    }

    songs.add(song);
    Collections.sort(songs);
  }

  public void removeSong(Song song) {
    if (!songs.contains(song)) {
      return;
    }

    songs.remove(song);
  }

  public Song getSmallestSong() {
    if (songs.isEmpty()) {
      throw new IllegalStateException("A playlist está vazia.");
    }

    Song smallest = null;
    for (Song s : songs) {
      if (smallest == null || s.getLength() < smallest.getLength()) {
        smallest = s;
      }
    }

    return smallest;
  }

  public Song getLargestSong() {
    if (songs.isEmpty()) {
      throw new IllegalStateException("A playlist está vazia.");
    }

    Song smallest = null;
    for (Song s : songs) {
      if (smallest == null || s.getLength() > smallest.getLength()) {
        smallest = s;
      }
    }

    return smallest;
  }

  public float getAverageSongLength() {
    if (songs.isEmpty()) {
      throw new IllegalStateException("A playlist está vazia.");
    }

    return getTotalLength() / songs.size();
  }

  public int getTotalLength() {
    int totalLength = 0;
    for (Song s : songs) {
      totalLength += s.getLength();
    }

    return totalLength;
  }

  public String getArtistsWithMostsSongs() {
    if (songs.isEmpty()) {
      throw new IllegalStateException("A playlist está vazia.");
    }

    HashMap<String, Integer> qty = new HashMap<String, Integer>();  // Mapeamos artista e qtd de músicas
    
    for (Song s : songs) {
      qty.put(s.getArtist(), qty.getOrDefault(s.getArtist(), 0) + 1);
    }

    String mostSongsArtist = null;
    int mostSongs = 0;
    for (String artist : qty.keySet()) {  // E depois procuramos o artista com mais músicas
      int songsQty = qty.get(artist);
      if (songsQty > mostSongs) {
        mostSongs = songsQty;
        mostSongsArtist = artist;
      }
    }

    return mostSongsArtist;
  }

  public Song play(boolean shuffle) {
    int position = songs.indexOf(currentSong);

    if (shuffle) {  // Se estiver no aleatório, mudamos a posição para uma aleatória válida (que existe) diferente da atual
      Random rand = new Random();
      int curPosition = position;
      while (position == curPosition) {
        position = rand.nextInt(songs.size());
      }
    } else {  // Caso contrário, avançamos uma posição e, se chegamos ao fim da playlist, voltamos ao início
      position++;

      if (position >= songs.size()) {
        position = 0;
      }
    }

    this.currentSong = songs.get(position);
    return currentSong;
  }

  public Song play() {
    return play(false);
  }

  public void transferTo(User target) {
    if (!target.isPremium() && songs.size() > 10) {
      throw new IllegalStateException("Playlist contém mais músicas do que a conta do usuário suporta.");
    }

    this.owner = target;
  }

  public User getOwner() {
    return owner;
  }
}
