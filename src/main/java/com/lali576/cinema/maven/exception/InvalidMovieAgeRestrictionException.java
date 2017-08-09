package com.lali576.cinema.maven.exception;

public class InvalidMovieAgeRestrictionException extends CinemaException {
    public InvalidMovieAgeRestrictionException(String message, String messageType) {
        super(message, messageType);
    }
    
}
