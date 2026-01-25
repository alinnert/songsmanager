package dev.alinnert.songsmanager.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Artist implements Serializable
{
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
	private List<Song> songs;

	public Artist() {}

	public Artist(String name) { this.name = name; }

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public List<Song> getSongs() { return songs; }

	public void setSongs(List<Song> songs) { this.songs = songs; }

	@Override
	public String toString() { return "[%d] %s".formatted(id, name); }

	public String toStringWithSongs() {
		var songs = this.songs
			.stream()
			.map(song -> "- [%d] %s".formatted(song.getId(), song.getName()))
			.collect(Collectors.joining("\n"));

		return "[%d] %s\nSongs of this artist:\n%s".formatted(id, name, songs);
	}
}
