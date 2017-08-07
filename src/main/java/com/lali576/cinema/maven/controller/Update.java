package com.lali576.cinema.maven.controller;

import com.lali576.cinema.maven.model.Seat;
import com.lali576.cinema.maven.model.Show;
import com.lali576.cinema.maven.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update {
    Connection conn = null;
    
    public Update(Connection conn) {
        this.conn = conn;
    }
    
    public void newTicket(Show show, Seat seat) throws SQLException {
        int seatID = seat.getID();
        int showID = show.getID();
        int movieID = show.getMovie().getID();
        
            PreparedStatement prep = conn.prepareStatement("INSERT INTO TICKETS (SHOWID, SEATID) VALUES (?, ?)");
            prep.setInt(1, showID);
            prep.setInt(2, seatID);
            prep.executeUpdate();
            
            prep = conn.prepareStatement("SELECT SOLDTICKETNUMBER FROM SOLDTICKETS WHERE MOVIEID = ?");
            prep.setInt(1, movieID);
            ResultSet rs2 = prep.executeQuery();
            
            if(rs2.next()) {
                int soldTicketNumber = rs2.getInt("SOLDTICKETNUMBER");
                soldTicketNumber++;
                
                prep = conn.prepareStatement("UPDATE SOLDTICKETS SET SOLDTICKETNUMBER=? WHERE MOVIEID = ?");
                prep.setInt(1, soldTicketNumber);
                prep.setInt(2, movieID);
                prep.executeUpdate();
                
            }
    }
    
    public void ticketRefuse(Ticket ticket) throws SQLException {
        int seatID = ticket.getSeatId();
        int showID = ticket.getShow().getID();
        int movieID = ticket.getShow().getMovie().getID();

            PreparedStatement prep = conn.prepareStatement("DELETE FROM TICKETS WHERE SHOWID = ? AND SEATID = ?");
            prep.setInt(1, showID);
            prep.setInt(2, seatID);
            prep.executeUpdate();
            
            prep = conn.prepareStatement("SELECT SOLDTICKETNUMBER FROM SOLDTICKETS WHERE MOVIEID = ?");
            prep.setInt(1, movieID);
            ResultSet rs2 = prep.executeQuery();
            
            if(rs2.next()) {
                int soldTicketNumber = rs2.getInt("SOLDTICKETNUMBER");
                soldTicketNumber--;
                
                prep = conn.prepareStatement("UPDATE SOLDTICKETS SET SOLDTICKETNUMBER=? WHERE MOVIEID = ?");
                prep.setInt(1, soldTicketNumber);
                prep.setInt(2, movieID);
                prep.executeUpdate();
            }
    }
}