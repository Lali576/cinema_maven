package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Show;
import javax.swing.JButton;

public class RoomButton extends JButton {
    private final Room room;
    private final Show show;
    
    public RoomButton(Room room, Show show) {
        this.room = room;
        this.show = show;
        setText("Jegy vasarlas");
    }
    
    public Room getRoom() {
        return room;
    }
    
    public Show getShow() {
        return show;
    }
}
