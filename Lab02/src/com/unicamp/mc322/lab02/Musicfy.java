package com.unicamp.mc322.lab02;

import com.unicamp.mc322.lab02.song.Playlist;
import com.unicamp.mc322.lab02.song.Song;
import com.unicamp.mc322.lab02.user.User;
import com.unicamp.mc322.lab02.user.UserGender;

public class Musicfy {
  public static void main(String[] args) {
    User user1 = new User("Marcos Paulo", "777.777.777-77", "26/01/2002", UserGender.MASCULINE);
    User user2 = new User("Cookiezi", "111.111.11-11", "04/03/1996", UserGender.MASCULINE);

    Song song1 = new Song("Seven Nation Army", "Rock", "The White Stripes", 232);
    Song song2 = new Song("Crazy Train", "Rock", "Ozzy Osbourne", 293);
    Song song3 = new Song("Feels", "Pop", "Calvin Harris", 223);
    Song song4 = new Song("Roar", "Pop", "Katy Perry", 224);
    Song song5 = new Song("Anima", "Hardcore", "Xi", 271);
    Song song6 = new Song("Freedom Dive", "Hardcore", "Xi", 270);
    Song song7 = new Song("Teo", "Hardcore", "Omoi", 210);
    Song song8 = new Song("Sleepwalking", "Metalcore", "Bring Me The Horizon", 230);

    Playlist rockPlaylist = new Playlist("Awesome Rock Songs", "Rock");
    rockPlaylist.addSong(song1);
    rockPlaylist.addSong(song2);

    Playlist osuPlaylist = new Playlist("Osu Memories", "hardcore");
    osuPlaylist.addSong(song5);
    osuPlaylist.addSong(song6);
    osuPlaylist.addSong(song7);

    Playlist metalcorePlaylist = new Playlist("Best of Metalcore", "Metalcore");
    metalcorePlaylist.addSong(song8);

    user1.addPlaylist(rockPlaylist);
    user1.addPlaylist(metalcorePlaylist);
    user2.addPlaylist(osuPlaylist);

    user1.showPlaylists();
    System.out.println("");
    user2.showInformation();

    Song asong1 = osuPlaylist.play();
    Song asong2 = osuPlaylist.play();
    Song asong3 = osuPlaylist.play(true);
  }
}