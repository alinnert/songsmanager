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
            .map(song -> "- %s".formatted(song.toString()))
            .toList();

        var stringBuilder = new StringBuilder();
        stringBuilder.append("%s\n".formatted(toString()));

        if (songs.isEmpty()) {
            stringBuilder.append("This playlist has no songs yet.");
        } else {
            if (songs.size() == 1) {
                stringBuilder.append("1 song in this playlist:\n");
            } else {
                stringBuilder.append(
                    "%d songs in this playlist:\n".formatted(songs.size()));
            }
            stringBuilder.append(String.join("\n", songs));
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() { return "[Playlist #%d] %s".formatted(id, name); }
}
