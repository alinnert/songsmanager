package dev.alinnert.songsmanager.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Song implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToMany
    @JoinTable(
        name = "playlist_songs",
        joinColumns = @JoinColumn(name = "song_id"),
        inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> playlists;

    public Song() {}

    public Song(String name, Artist artist) {
        this.name = name;
        this.artist = artist;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Artist getArtist() { return artist; }

    public void setArtist(Artist artist) { this.artist = artist; }

    public List<Playlist> getPlaylists() { return playlists; }

    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    public String toStringWithPlaylists() {
        var playlists = this.playlists
            .stream()
            .map(pl -> "- %s".formatted(pl.toString()))
            .toList();

        var stringBuilder = new StringBuilder();
        stringBuilder.append("%s\n".formatted(toString()));

        if (playlists.isEmpty()) {
            stringBuilder.append("This song is not in any playlist yet.");
        } else {
            stringBuilder
                .append("Playlists this song is in:\n")
                .append(String.join("\n", playlists));
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "[Song #%d] %s by [Artist #%d] %s".formatted(
            id, name, artist.getId(), artist.getName());
    }
}
