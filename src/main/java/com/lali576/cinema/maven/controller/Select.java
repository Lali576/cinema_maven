package com.lali576.cinema.maven.controller;

import com.lali576.cinema.maven.model.Movie;
import com.lali576.cinema.maven.model.Register;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Seat;
import com.lali576.cinema.maven.model.Show;
import com.lali576.cinema.maven.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Select {
    Connection conn = null;
    
    public Select(Connection conn) {
        this.conn = conn;
    }
    
    public Register getRegister(final String username, final String password) {
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT USERNAME, ISADMIN FROM REGISTERS WHERE USERNAME = ? AND PASSWORD = ?");
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            
            if(rs.next()) {
                String userName = rs.getString("USERNAME");
                boolean isAdmin = rs.getBoolean("ISADMIN");
                return new Register(userName, isAdmin);
            }
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MOVIES");
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String country = rs.getString("COUNTRY");
                boolean isDubbed = rs.getBoolean("ISDUBBED");
                String director = rs.getString("DIRECTOR");
                String synopsis = rs.getString("SYNOPSIS");
                int length = rs.getInt("LENGTH");
                
                Movie movie = new Movie(id, title, country, isDubbed, director, synopsis, length);
                movie.setAge(getAgeByMovieId(id));
                movie.setSoldTickets(getSoldTicketByMovieId(id));
                movie.setMaxPlay(getMaxPlayByMovieId(id));
                movie.setShows(getShowsByMovieId(id));
                
                movies.add(movie);
            }
            
            return movies;
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public Movie getMovieById(int movieID) {
        Movie movie = null;
        
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM MOVIES WHERE ID = ?");
            prep.setInt(1, movieID);
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String country = rs.getString("COUNTRY");
                boolean isDubbed = rs.getBoolean("ISDUBBED");
                String director = rs.getString("DIRECTOR");
                String synopsis = rs.getString("SYNOPSIS");
                int length = rs.getInt("LENGTH");
                
                movie = new Movie(id, title, country, isDubbed, director, synopsis, length);
                movie.setAge(getAgeByMovieId(id));
                movie.setSoldTickets(getSoldTicketByMovieId(id));
                movie.setMaxPlay(getMaxPlayByMovieId(id));
            }
            
            return movie;
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public int getAgeByMovieId(int movieId) {
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT AGERESTRICTION FROM AGE WHERE MOVIEID = ?");
            prep.setInt(1, movieId);
            ResultSet rs = prep.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("AGERESTRICTION");
            }
        } catch(SQLException e) {
            
        }
        
        return 0;
    }
    
    public int getSoldTicketByMovieId(int movieId) {
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT SOLDTICKETNUMBER FROM SOLDTICKETS WHERE MOVIEID = ?");
            prep.setInt(1, movieId);
            ResultSet rs = prep.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("SOLDTICKETNUMBER");
            }
        } catch(SQLException e) {
            
        }
        
        return 0;
    }
    
    public int getMaxPlayByMovieId(int movieId) {
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT MAXPLAYNUMBER FROM MAXPLAY WHERE MOVIEID = ?");
            prep.setInt(1, movieId);
            ResultSet rs = prep.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("MAXPLAYNUMBER");
            }
        } catch(SQLException e) {
            
        }
        
        return 0;
    }
    
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS");
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                String roomName = rs.getString("ROOMNAME");
                int roomRow = rs.getInt("ROOMROWS");
                int roomColumn = rs.getInt("ROOMCOLUMNS");
                
                Room room = new Room(id, roomName, roomRow, roomColumn);
                room.setSeats(getSeatsByRoomId(id, roomRow, roomColumn));
                room.setShows(getShowsByRoomId(id));
                rooms.add(room);
            }
            
            return rooms;
        } catch(SQLException e) {
           
        }
        
        return null;
    }
    
    public Room getRoomById(int roomID) {
        Room room = null;
        
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM ROOMS WHERE ID = ?");
            prep.setInt(1, roomID);
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                String roomName = rs.getString("ROOMNAME");
                int roomRow = rs.getInt("ROOMROWS");
                int roomColumn = rs.getInt("ROOMCOLUMNS");
                
                room = new Room(id, roomName, roomRow, roomColumn);
                room.setSeats(getSeatsByRoomId(id, roomRow, roomColumn));              
            }
            
            return room;
        } catch(SQLException e) {
           
        }
        
        return null;
    }
    
    public List<List<Seat>> getSeatsByRoomId(int roomID, int roomRows, int roomColumns) {
        List<List<Seat>> seats = new ArrayList<>();
        
        try{
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM SEATS WHERE ROOMID = ?");
            prep.setInt(1, roomID);
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                for (int i = 0; i < roomRows; i++) {
                    List<Seat> row = new ArrayList<>();
                    for (int j = 0; j < roomColumns; j++) {
                        int id = rs.getInt("ID");
                        int rowNumber = rs.getInt("ROWNUMBER");
                        int columnNumber = rs.getInt("COLUMNNUMBER");
                        
                        row.add(new Seat(id, rowNumber, columnNumber));
                        rs.next();
                    }
                    seats.add(row);
                }
                
            }
            
            return seats;
        } catch(SQLException e) {
           
        }
        
        return null;
    }
    
    public List<Show> getShows() {
        List<Show> shows = new ArrayList<>();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SHOWS");
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                int movieID = rs.getInt("MOVIEID");
                int roomID = rs.getInt("ROOMID");
                String startTime = rs.getString("STARTTIME");
                
                Show show = new Show(id, startTime);
                show.setMovie(getMovieById(movieID));
                show.setRoom(getRoomById(roomID));
                show.setTickets(getTicketsByShowId(id, show));
                shows.add(show);
            }
            
            return shows;
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public List<Show> getShowsByRoomId(int roomID) {
        List<Show> shows = new ArrayList<>();
        
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM SHOWS WHERE ROOMID = ?");
            prep.setInt(1, roomID);
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                int movieID = rs.getInt("MOVIEID");
                int roomID2 = rs.getInt("ROOMID");
                String startTime = rs.getString("STARTTIME");
                
                Show show = new Show(id, startTime);
                show.setMovie(getMovieById(movieID));
                show.setRoom(getRoomById(roomID2));
                show.setTickets(getTicketsByShowId(id, show));
                
                shows.add(show);
            }
            
            return shows;
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public List<Show> getShowsByMovieId(int movieID) {
        List<Show> shows = new ArrayList<>();
        
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM SHOWS WHERE MOVIEID = ?");
            prep.setInt(1, movieID);
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("ID");
                int movieID2 = rs.getInt("MOVIEID");
                int roomID = rs.getInt("ROOMID");
                String startTime = rs.getString("STARTTIME");
                
                Show show = new Show(id, startTime);
                show.setMovie(getMovieById(movieID2));
                show.setRoom(getRoomById(roomID));
                show.setTickets(getTicketsByShowId(id, show));
                
                shows.add(show);
            }
            
            return shows;
        } catch(SQLException e) {
            
        }
        
        return null;
    }
    
    public List<Show> getShowsByRoomName(String roomName) {
        List<Show> shows = new ArrayList<>();
        
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT ID FROM ROOMS WHERE ROOMNAME LIKE ?");
            prep.setString(1, roomName + '%');
            
            ResultSet rs = prep.executeQuery();
            if(rs.next()) {
                int roomID = rs.getInt("ID");
                 return getShowsByRoomId(roomID);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Select.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<Show> getShowsByMovieTitle(String title) {
        List<Show> shows = new ArrayList<>();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT ID FROM MOVIES WHERE TITLE LIKE ?");
            prep.setString(1, title + '%');
            
            ResultSet rs = prep.executeQuery();
            if(rs.next()) {
                int movieID = rs.getInt("ID");
                return getShowsByMovieId(movieID);
            }
        } catch (SQLException ex) {
            
        }
        
        
        return null;
    }
    
    public List<Ticket> getTicketsByShowId(int showID, Show show) {
        List<Ticket> tickets = new ArrayList<>();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT * FROM TICKETS WHERE SHOWID = ?");
            prep.setInt(1, showID);
            
            ResultSet rs = prep.executeQuery();
            
            while(rs.next()) {
                int showID2 = rs.getInt("SHOWID");
                int seatID = rs.getInt("SEATID");
                Ticket ticket = new Ticket(seatID);
                ticket.setShow(show);
                tickets.add(ticket);
            }
            
            return tickets;
        } catch(SQLException ex) {
            
        } 
        
        return null;
    }
}