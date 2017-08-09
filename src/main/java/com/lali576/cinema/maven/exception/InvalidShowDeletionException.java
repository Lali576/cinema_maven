package com.lali576.cinema.maven.exception;

public class InvalidShowDeletionException extends CinemaException{
    public InvalidShowDeletionException(final String message, final String messageType) {
        super(message, messageType);
    }
}
