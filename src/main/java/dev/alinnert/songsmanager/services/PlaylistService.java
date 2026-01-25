package dev.alinnert.songsmanager.services;

import dev.alinnert.songsmanager.entities.Playlist;
import dev.alinnert.songsmanager.persistence.PersistenceService;

public class PlaylistService
{
    private final PersistenceService persistenceService;

    public PlaylistService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void addPlaylist() {
        var playlistName = IO.readln("Playlist name: ");
        var playlist = new Playlist(playlistName);

        try (var em = persistenceService.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(playlist);
            em.getTransaction().commit();
        }
    }

    public void removePlaylist() {
        var playlistId = Long.parseLong(IO.readln("Playlist ID: "));

        try (var em = persistenceService.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.getReference(Playlist.class, playlistId));
            em.getTransaction().commit();
        }
    }

    public void listPlaylists() {
        try (var em = persistenceService.getEntityManager()) {
            var playlists = em
                .createQuery("SELECT p FROM Playlist p ORDER BY name",
                    Playlist.class
                )
                .getResultList();

            if (playlists.isEmpty()) {
                IO.println("No playlists found.");
                return;
            }

            playlists.forEach(IO::println);
        }
    }

    public void getPlaylist() {
        var playlistId = Long.parseLong(IO.readln("Playlist ID: "));
        IO.println();

        try (var em = persistenceService.getEntityManager()) {
            var playlist = em.find(Playlist.class, playlistId);
            if (playlist == null) {
                IO.println(
                    "Playlist with ID %d not found.".formatted(playlistId));
            } else {
                IO.println(playlist.toStringWithSongs());
            }
        }
    }

    public void addSongToPlaylist() {
        IO.println("Not implemented yet!");
    }

    public void removeSongFromPlaylist() {
        IO.println("Not implemented yet!");
    }
}
