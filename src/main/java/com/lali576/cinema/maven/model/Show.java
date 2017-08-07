package com.lali576.cinema.maven.model;

import java.util.List;

public class Show implements Comparable<Show>{

    private final int id;
    private Movie movie;
    private Room room;
    private final String startTime;
    private List<Ticket> tickets;

    public Show(int id,
            String startTime) {
        this.id = id;
        this.movie = null;
        this.room = null;
        this.startTime = startTime;
        this.tickets = null;
    }
    
    public static Show createShow(Movie movie, Room room, String startTime) {
        Show show = new Show(0, startTime);
        show.setMovie(movie);
        show.setRoom(room);
        
        return show;
    }

    public int getID() {
        return id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public String getStartTime() {
        return startTime;
    }
    
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public int compareTo(Show that) {
        String[] thisDatas = this.getStartTime().split(":");
        String[] thatDatas = startTime.split(":");
        int thisHour = Integer.parseInt(thisDatas[0]);
        int thisMinute = Integer.parseInt(thisDatas[1]);
        int thatHour = Integer.parseInt(thatDatas[0]);
        int thatMinute = Integer.parseInt(thatDatas[1]);
        
        if(thisHour > thatHour) {
            return -1;
        } else if(thisHour == thatHour) {
            if(thisMinute > thatMinute) {
                return -1;
            } else if(thisMinute <= thatMinute) {
                return 1;
            }
        } else if(thisHour < thatHour) {
            return 1;
        }
        
        return 0;
    }
    
    public boolean isShowFull() {
        return tickets.size() == room.getSeats().size();
    }
    
    public boolean isTicketFree() {
        return tickets.isEmpty();
    }
}
