package com.lali576.cinema.maven.exception;

public class InvalidUserException extends CinemaException {
    public InvalidUserException(final String message, final String messageType) {
        super(message, messageType);
    }
}
