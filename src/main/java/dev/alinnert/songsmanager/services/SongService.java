package dev.alinnert.songsmanager.services;

import dev.alinnert.songsmanager.entities.Artist;
import dev.alinnert.songsmanager.entities.Song;
import dev.alinnert.songsmanager.persistence.PersistenceService;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

public class SongService
{
	private final PersistenceService persistenceService;

	public SongService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void addSong() {
		var songName = IO.readln("Song name: ");
		var artistId = Long.parseLong(IO.readln("Artist ID: "));

		try (var em = persistenceService.getEntityManager()) {
			var artistRef = em.getReference(Artist.class, artistId);
			var song = new Song(songName, artistRef);
			em.getTransaction().begin();
			em.persist(song);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				IO.println(
					"Artist with ID %s does not exist. Song was not added.".formatted(
						artistId));
				return;
			}
			IO.println("Failed to add song!");
			throw e;
		}
	}

	public void removeSong() {
		var songId = IO.readln("Song ID: ");
		try (var em = persistenceService.getEntityManager()) {
			em.getTransaction().begin();
			em.remove(em.getReference(Song.class, Long.parseLong(songId)));
			em.getTransaction().commit();
		}
	}

	public void listSongs() {
		try (var em = persistenceService.getEntityManager()) {
			em.createQuery("SELECT s FROM Song s ORDER BY name", Song.class)
				.getResultList()
				.forEach(IO::println);
		}
	}

	public void getSong() {
		var songId = Long.parseLong(IO.readln("Song ID: "));
		IO.println();

		try (var em = persistenceService.getEntityManager()) {
			IO.println(em.find(Song.class, songId));
		}
	}
}
