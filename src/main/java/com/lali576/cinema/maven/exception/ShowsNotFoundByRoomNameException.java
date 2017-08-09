/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lali576.cinema.maven.exception;

/**
 *
 * @author Tóth Ádám
 */
public class ShowsNotFoundByRoomNameException extends CinemaException {
    public ShowsNotFoundByRoomNameException(String message, String messageType) {
        super(message, messageType);
    }    
}
