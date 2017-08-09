package com.lali576.cinema.maven.exception;

public class ShowsNotFoundByMovieTitleException extends CinemaException {
    public ShowsNotFoundByMovieTitleException(String message, String messageType) {
        super(message, messageType);
    }   
}
