package dev.alinnert.songsmanager.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Playlist implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany
    private List<Song> songs;
}
