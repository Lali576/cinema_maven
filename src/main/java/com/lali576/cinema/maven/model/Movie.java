package com.lali576.cinema.maven.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private final int id;
    private final String title;
    private final String country;
    private final boolean isDubbed;
    private final String director;
    private final String synopsis;
    private final int length;
    private int age;
    private int soldTickets;
    private int maxPlay;
    private List<Show> shows;

    public Movie(
            int id,
            String title,
            String country,
            boolean isDubbed,
            String director,
            String synopsis,
            int length
    ) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.isDubbed = isDubbed;
        this.director = director;
        this.synopsis = synopsis;
        this.length = length;
        this.age = 0;
        this.soldTickets = 0;
        this.maxPlay = 0;
        this.shows = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getLength() {
        return length;
    }

    public boolean getIsDubbed() {
        return isDubbed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
    
    public int getSoldTickets() {
        return soldTickets;
    }

    public void setMaxPlay(int maxPlay) {
        this.maxPlay = maxPlay;
    }
    
    public int getMaxPlay() {
        return maxPlay;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public List<Show> getShows() {
        return shows;
    }
}