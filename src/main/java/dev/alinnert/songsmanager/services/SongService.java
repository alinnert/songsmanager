package dev.alinnert.songsmanager.services;

import dev.alinnert.songsmanager.entities.Artist;
import dev.alinnert.songsmanager.entities.Song;
import dev.alinnert.songsmanager.persistence.PersistenceService;

public class SongService
{
	private final PersistenceService persistenceService;

	public SongService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void addSong() {
		IO.println("Song name: ");
		var name = IO.readln();
		IO.println("Artist ID: ");
		var artistId = IO.readln();

		Artist artist = persistenceService
			.getEntityManager()
			.find(Artist.class, Long.parseLong(artistId));

		var song = new Song(name, artist);
		persistenceService.runWithTransaction(em -> em.persist(song));
	}

	public void removeSong() {
		IO.println("Song ID: ");
		var id = IO.readln();
		persistenceService.runWithTransaction(
			em -> em.remove(em.getReference(Song.class, Long.parseLong(id))));
	}

	public void listSongs() {
		persistenceService
			.getEntityManager()
			.createQuery("SELECT s FROM Song s", Song.class)
			.getResultList()
			.forEach(IO::println);
	}

	public void getSong() {
		IO.println("Not implemented yet!");
	}
}
