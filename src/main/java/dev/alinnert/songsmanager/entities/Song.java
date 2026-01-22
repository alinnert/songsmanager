package dev.alinnert.songsmanager.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Song implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Artist artist;
    @ManyToMany
    private List<Playlist> playlists;
}
