package dev.alinnert.songsmanager.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Playlist implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "playlist_songs",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id"),
        uniqueConstraints = @UniqueConstraint(
            columnNames = { "playlist_id", "song_id" }
        )
    )
    private List<Song> songs;

    public Playlist() {}

    public Playlist(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Song> getSongs() { return songs; }

    public void setSongs(List<Song> songs) { this.songs = songs; }

    public String toStringWithSongs() {
        var songs = this.songs
            .stream()
            .map(song -> "- [Song #%d] %s\n".formatted(
                song.getId(),
                song.getName()
            ))
            .toList();

        var stringBuilder = new StringBuilder();
        stringBuilder.append("%s\n".formatted(toString()));

        if (songs.isEmpty()) {
            stringBuilder.append("This playlist has no songs yet.");
        } else {
            stringBuilder
                .append("Songs in this playlist:\n")
                .append(String.join("", songs));
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "[Playlist #%d] %s (containing %d %s)".formatted(
            id, name, songs.size(), songs.size() == 1 ? "song" : "songs");
    }
}
