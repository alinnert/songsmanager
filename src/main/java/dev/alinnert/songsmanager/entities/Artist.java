package dev.alinnert.songsmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;

@Entity
public class Artist implements Serializable
{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToMany
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
}
