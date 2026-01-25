package dev.alinnert.songsmanager;

import dev.alinnert.songsmanager.persistence.PersistenceService;
import dev.alinnert.songsmanager.services.ArtistService;
import dev.alinnert.songsmanager.services.PlaylistService;
import dev.alinnert.songsmanager.services.SongService;

public class SongsManager
{
	void main() {
		var msg = """
			Welcome to Songs Manager!
			
			Choose an option:
			[ Artists ]
			  add a         Add artist
			  rm a          Remove artist
			  ls a          List artists
			  get a			Get artist
			[ Songs ]
			  add s         Add song
			  rm s          Remove song
			  ls s          List songs
			  get s			Get song
			[ Playlists ]
			  add pl        Add playlist
			  rm pl         Remove playlist
			  ls pl         List playlists
			  get pl		Get playlist
			[ Playlist songs ]
			  add s to pl   Add song to playlist
			  rm s from pl  Remove song from playlist
			[ App ]
			  exit          Exit app
			""";

		IO.println(msg);

		var choice = IO.readln("Your choice: ").trim().toLowerCase();
		if (choice.isBlank() || choice.equals("exit")) {
			IO.println("Bye! 👋");
			System.exit(0);
		}

		IO.println();

		try (var ps = new PersistenceService()) {
			switch (choice) {
				case "add a" -> new ArtistService(ps).addArtist();
				case "rm a" -> new ArtistService(ps).removeArtist();
				case "ls a" -> new ArtistService(ps).listArtists();
				case "get a" -> new ArtistService(ps).getArtist();
				case "add s" -> new SongService(ps).addSong();
				case "rm s" -> new SongService(ps).removeSong();
				case "ls s" -> new SongService(ps).listSongs();
				case "get s" -> new SongService(ps).getSong();
				case "add pl" -> new PlaylistService(ps).addPlaylist();
				case "rm pl" -> new PlaylistService(ps).removePlaylist();
				case "ls pl" -> new PlaylistService(ps).listPlaylists();
				case "get pl" -> new PlaylistService(ps).getPlaylist();
				case "add s to pl" ->
					new PlaylistService(ps).addSongToPlaylist();
				case "rm s from pl" ->
					new PlaylistService(ps).removeSongFromPlaylist();
				default -> IO.println("Invalid choice!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
