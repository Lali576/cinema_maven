package com.lali576.cinema.maven.controller;

import com.lali576.cinema.maven.exception.InvalidShowDeletionException;
import com.lali576.cinema.maven.exception.ShowDatabaseException;
import com.lali576.cinema.maven.model.Show;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

    Connection conn = null;

    public Delete(Connection conn) {
        this.conn = conn;
    }

    public void deleteShow(Show show) throws ShowDatabaseException, InvalidShowDeletionException {
        try {
            if (show.isTicketFree()) {
                int id = show.getID();
                PreparedStatement prep = conn.prepareStatement("DELETE FROM SHOWS WHERE ID = ?");
                prep.setInt(1, id);
                prep.executeUpdate();
            } else {
                throw new InvalidShowDeletionException("A megadott előadásra még van foglalt jegy!", "Törlési hiba!");
            }
        } catch(SQLException ex) {
            throw new ShowDatabaseException("Az előadás törlése közben hiba lépett fel!", "Adatbázis hiba!");
        }

    }
}
