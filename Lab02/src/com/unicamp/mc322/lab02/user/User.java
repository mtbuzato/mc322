package com.unicamp.mc322.lab02.user;

import java.util.ArrayList;

import com.unicamp.mc322.lab02.song.Playlist;
import com.unicamp.mc322.lab02.song.Song;

public class User {
  private String name;
  private String cpf;
  private String birthdate;
  private UserGender gender;
  private boolean premium;
  private ArrayList<Playlist> playlists;
  
  public User(String name, String cpf, String birthdate, UserGender gender) {
    this.name = name;
    this.cpf = cpf;
    this.gender = gender;
    this.birthdate = birthdate;
    this.premium = false;
    this.playlists = new ArrayList<Playlist>();
  }

  public void showInformation() {
    System.out.printf("Nome: %s\n", name);
    System.out.printf("CPF: %s\n", cpf);
    System.out.printf("Data de Nascimento: %s\n", birthdate);
    System.out.printf("Gênero: %s\n", gender.getOutput());
    System.out.printf("Assinante: %s\n", isPremium() ? "SIM" : "NÃO");
  }

  public void showPlaylists() {
    System.out.printf("User: %s\n", name);
    System.out.printf("Number of Playlists: %d\n", playlists.size());

    for (int i = 0; i < playlists.size(); i++) {
      Playlist p = playlists.get(i);
      System.out.printf("Playlist %d: %s\n", i + 1, p.getName());
      System.out.printf("   Number of Songs: %d\n", p.getSongsCount());
      System.out.printf("   Songs:\n");

      for (Song s : p.getSongs()) {
        System.out.printf("   - %s\n", s.getDisplayName());
      }
    }
  }

  public void addPlaylist(Playlist playlist) {
    if (isPremium() && playlists.size() == 10) {
      throw new IllegalStateException("Operação não permitida: a lista de playlists está cheia.");
    }

    if (!isPremium() && playlists.size() == 3) {
      throw new IllegalStateException("Operação não permitida: a lista de playlists está cheia. Assine agora e crie até 10 playlists!");
    }

    if (playlists.contains(playlist)) {
      return;
    }

    playlists.add(playlist);
    playlist.transferTo(this);
  }

  public void deletePlaylist(Playlist playlist) {
    if (!playlists.contains(playlist)) {
      return;
    }

    playlists.remove(playlist);
  }

  public void transferPlaylist(Playlist list, User target) {
    target.addPlaylist(list);
    deletePlaylist(list);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCPF() {
    return cpf;
  }

  public void setCPF(String cpf) {
    this.cpf = cpf;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

  public UserGender getGender() {
    return gender;
  }

  public void setGender(UserGender gender) {
    this.gender = gender;
  }

  public void setGender(String gender) {
    this.gender = UserGender.valueOf(gender.toUpperCase());
  }

  public boolean isPremium() {
    return premium;
  }

  public void setPremium(boolean premium) {
    if (premium && !this.premium) { // Se o usuário deixou de ser assinante
      ArrayList<Playlist> compatiblePlaylists = new ArrayList<Playlist>();

      for (Playlist p : playlists) {  // Removemos todas as playlists que tem mais de 10 músicas
        if (p.getSongsCount() <= 10) {
          compatiblePlaylists.add(p);
        } else {
          deletePlaylist(p);
        }
      }

      if (compatiblePlaylists.size() > 3) { // E, se sobrarem mais que 3, removemos do fim da lista de playlists até sobrarem 3
        for (int i = 4; i < compatiblePlaylists.size(); i++) {
          deletePlaylist(compatiblePlaylists.get(i));
        }
      }
    }

    this.premium = premium;
  }
}
