package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.exception.RoomDatabaseException;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Seat;
import com.lali576.cinema.maven.model.Show;
import com.lali576.cinema.maven.model.Ticket;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class RoomPanel extends JPanel {
    public final Logic logic;
    private Room room;
    private final Show show;
    private final MainFrame mainFrame;
    public JButton ticketController;
    
    public RoomPanel(Logic logic, Room room, Show show, MainFrame mainFrame) {
        this.logic = logic;
        this.room = room;
        this.show = show;
        this.mainFrame = mainFrame;
        setupRoomPanel();
    }
    
    private void setupRoomPanel() {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new GetBackToShowPanel());
        add(backButton);
        JLabel roomNameLabel = new JLabel(room.getRoomName());
        add(roomNameLabel);
        JPanel seats = new JPanel();
        int rows = room.getSeats().size();
        int columns = room.getSeats().get(0).size();
        seats.setLayout(new GridLayout(rows, columns));
        
        for (int i = rows-1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                Seat seat = room.getSeats().get(i).get(j);
                Ticket ticket = null;
                for(Ticket t : show.getTickets()) {
                    if(t.getSeatId() == seat.getID()) {
                        ticket = t;
                        break;
                    }
                }
                SeatButton seatButton = new SeatButton(seat, ticket);               
                seatButton.addActionListener(new ShowButton());
                seats.add(seatButton);
            }
        }
        add(seats);
        ticketController = new JButton();
        ticketController.setVisible(false);
        add(ticketController);
        revalidate();
        repaint();
    }
    
    private class GetBackToShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            mainFrame.getShowPanelByRoomPanel();
        } 
    }
    
    private class ShowButton implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            SeatButton seatButton = (SeatButton)e.getSource();
            if(seatButton.isSelected()) {
                seatButton.setSelected(false);
                seatButton.setBorder(null);
            } else {
                seatButton.setSelected(true);
                seatButton.setBorder(new LineBorder(Color.BLACK, 5));
            }
            //seatButton.repaint();
            if(seatButton.getColor() == Color.green && seatButton.getTicket() == null) {
                ticketController.setText("Jegy vasarlas");
            } else {
                ticketController.setText("Jegy visszavonas");
            }
            
            Seat seat = seatButton.getSeat();
            ticketController.addActionListener(new TicketController(show, seatButton));
            ticketController.setVisible(true);
        } 
    }
    
    private class TicketController implements ActionListener {
        private final Show show;
        private final SeatButton seatButton;
        
        public TicketController(Show show, SeatButton seatButton) {
            this.show = show;
            this.seatButton = seatButton;
        }
        
       @Override
        public void actionPerformed(ActionEvent e) {                      
            try {
                if(seatButton.getColor() == Color.green && seatButton.getTicket() == null) {
                    logic.update.newTicket(show, seatButton.getSeat());
                } else if (seatButton.getColor() == Color.red && seatButton.getTicket() != null) {
                    logic.update.ticketRefuse(seatButton.getTicket());               
                }
                room = logic.select.getRoomById(room.getID());
                setupRoomPanel();
            } catch(RoomDatabaseException ex) {
                ex.writeOutEception();
            } catch(SQLException ex) {
            }
        } 
    }
}
