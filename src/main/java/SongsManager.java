import dev.alinnert.songsmanager.services.ArtistService;
import dev.alinnert.songsmanager.services.PersistenceService;

/**
 *
 * @author andreaslinnert
 */
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

	try (var ps = new PersistenceService()) {
		switch (choice) {
			case "add a" -> new ArtistService(ps).addArtist();
			case "rm a" -> new ArtistService(ps).removeArtist();
			case "ls a" -> new ArtistService(ps).listArtists();
			default -> IO.println("Invalid choice!");
		}
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
}
