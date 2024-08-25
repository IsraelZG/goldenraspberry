package com.igianesini.goldenraspberry.domain;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_year")
    private int year;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "movie_studio",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private Set<Studio> studios = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<Producer> producers = new HashSet<>();

    private boolean winner;

    public Movie() {
    }

    public Movie(int year, String title,  boolean winner, Set<Studio> studios, Set<Producer> producers) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    public Set<Studio> getStudios() {
        return studios;
    }

    public void setStudios(Set<Studio> studios) {
        this.studios = studios;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
