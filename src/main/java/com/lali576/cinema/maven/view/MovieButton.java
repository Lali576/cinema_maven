package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.model.Movie;
import javax.swing.JButton;

public class MovieButton extends JButton {
    private final Movie movie;
    
    public MovieButton(Movie movie) {
        this.movie = movie;
        setText(movie.getTitle());
    }
    
    public Movie getMovie() {
        return movie;
    }
}
