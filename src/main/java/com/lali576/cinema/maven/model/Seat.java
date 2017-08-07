package com.lali576.cinema.maven.model;

public class Seat {
    private final int id;
    private Room room;
    private final int rowNumber;
    private final int columnNumber;

    public Seat(int id,
                int rowNumber,
                int columnNumber) {
        this.id = id;
        this.room = null;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }
    
    public int getID() {
        return id;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}