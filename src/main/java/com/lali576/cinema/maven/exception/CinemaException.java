package com.lali576.cinema.maven.exception;

import javax.swing.JOptionPane;

public class CinemaException extends Exception {
    private final String message;
    private final String messageType;
    
    public CinemaException(final String message, final String messageType) {
        this.message = message;
        this.messageType = messageType;
    }
    
    public void writeOutEception() {
        JOptionPane.showMessageDialog(null, message, messageType, JOptionPane.INFORMATION_MESSAGE);
    }
}
