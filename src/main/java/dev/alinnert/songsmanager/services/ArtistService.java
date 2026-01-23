package dev.alinnert.songsmanager.services;

import dev.alinnert.songsmanager.entities.Artist;
import dev.alinnert.songsmanager.persistence.PersistenceService;

public class ArtistService
{
	private final PersistenceService persistenceService;

	public ArtistService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void addArtist() {
		IO.print("Artist name: ");
		var name = IO.readln();
		var artist = new Artist(name);
		persistenceService.runWithTransaction(em -> em.persist(artist));
	}

	public void removeArtist() {
		IO.print("Artist ID: ");
		var id = IO.readln();
		persistenceService.runWithTransaction(
			em -> em.remove(em.getReference(Artist.class, Long.parseLong(id))));
	}

	public void listArtists() {
		persistenceService.getEntityManager()
			.createQuery("SELECT a FROM Artist a", Artist.class)
			.getResultList().forEach(IO::println);
	}
}
