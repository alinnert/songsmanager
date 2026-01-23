package dev.alinnert.songsmanager;

import dev.alinnert.songsmanager.persistence.PersistenceService;
import dev.alinnert.songsmanager.services.ArtistService;
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
			[ Songs ]
			  add s         Add song
			  rm s          Remove song
			  ls s          List songs
			[ Playlists ]
			  add pl        Add playlist
			  rm pl         Remove playlist
			  ls pl         List playlists
			  add to pl     Add song to playlist
			  rm from pl    Remove song from playlist
			[ App ]
			  exit          Exit app
			""";

		IO.println(msg);
		IO.print("Your choice: ");

		var choice = IO.readln();
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
				case "add s" -> new SongService(ps).addSong();
				case "rm s" -> new SongService(ps).removeSong();
				case "ls s" -> new SongService(ps).listSongs();
				default -> IO.println("Invalid choice!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
