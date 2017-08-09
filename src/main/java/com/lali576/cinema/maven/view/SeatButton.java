package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.model.Seat;
import com.lali576.cinema.maven.model.Ticket;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class SeatButton extends JButton {
    private final Seat seat;
    private final Ticket ticket;
    
    public SeatButton(Seat seat, Ticket ticket) {
        this.seat = seat;
        this.ticket = ticket;
        setupSeatButton();
    }
    
    private void setupSeatButton() {
        setPreferredSize(new Dimension(40, 40));
        setBounds(10, 10, 40, 40);
        if(ticket == null) {
           setBackground(Color.green);
        } else {
           setBackground(Color.red);
        }
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public Ticket getTicket() {
        return ticket;
    }
    
    public Color getColor() {
        return getBackground();
    }
}
