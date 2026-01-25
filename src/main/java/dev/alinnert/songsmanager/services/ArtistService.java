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
		var name = IO.readln("Artist name: ");
		var artist = new Artist(name);

		try (var em = persistenceService.getEntityManager()) {
			em.getTransaction().begin();
			em.persist(artist);
			em.getTransaction().commit();
		}
	}

	public void removeArtist() {
		var id = Long.parseLong(IO.readln("Artist ID: "));

		try (var em = persistenceService.getEntityManager()) {
			em.getTransaction().begin();
			em.remove(em.getReference(Artist.class, id));
			em.getTransaction().commit();
		}
	}

	public void listArtists() {
		try (var em = persistenceService.getEntityManager()) {
			em
				.createQuery("SELECT a FROM Artist a", Artist.class)
				.getResultList()
				.forEach(IO::println);
		}
	}

	public void getArtist() {
		var artistId = Long.parseLong(IO.readln("Artist ID: "));
		IO.println();

		try (var em = persistenceService.getEntityManager()) {
			IO.println(em.find(Artist.class, artistId).toStringWithSongs());
		}
	}
}
