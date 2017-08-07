package com.lali576.cinema.maven.model;

public class Ticket {
    private Show show;
    private final int seatID;
    
    public Ticket(final int seatID) {
        this.seatID = seatID;
    }

    public void setShow(Show show) {
        this.show = show;
    }
    
    public Show getShow() {
        return show;
    }

    public int getSeatId() {
        return seatID;
    }
}
