package com.lali576.cinema.maven;

//import hu.elte.wr14yr.controller.Logic;
import com.lali576.cinema.maven.view.MainFrame;
import java.sql.SQLException;

public class Boot {
    
    //Program betolto main metodus
    public static void main(String[] args) throws SQLException {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (SQLException ex) {
                
            }
        });
        /*
        Logic logic = new Logic();
        logic.insert.fillUpWithRegs();
        logic.insert.fillUpWithMovies();
        logic.insert.fillUpWithRooms();
        */
    }
}
