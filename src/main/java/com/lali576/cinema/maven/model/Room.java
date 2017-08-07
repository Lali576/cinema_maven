package com.lali576.cinema.maven.model;

import java.util.List;

public class Room {
    private final int id;
    private final String roomName;
    private final int roomRow;
    private final int roomColumn;
    private List<List<Seat>> seats;
    private List<Show> shows;

    public Room(int id,
                String roomName, 
                int roomRow, 
                int roomColumn) {
        this.id = id;
        this.roomName = roomName;
        this.roomRow = roomRow;
        this.roomColumn = roomColumn;
        this.seats = null;
        this.shows = null;
    }

    public int getID() {
        return id;
    }
    
    public String getRoomName() {
        return roomName;
    }

    public int getRoomRows() {
        return roomRow;
    }

    public int getRoomColumns() {
        return roomColumn;
    }
    
    public void setSeats(List<List<Seat>> seats) {
        this.seats = seats;
    }
    
    public List<List<Seat>> getSeats() {
        return seats;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public List<Show> getShows() {
        return shows;
    }
}