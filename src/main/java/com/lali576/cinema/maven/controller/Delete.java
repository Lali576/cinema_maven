package com.lali576.cinema.maven.controller;

import com.lali576.cinema.maven.model.Show;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    Connection conn = null;
    
    public Delete(Connection conn) {
        this.conn = conn;
    }
  
    public void deleteShow(Show show) throws SQLException {              
        int id = show.getID();
        PreparedStatement prep = conn.prepareStatement("DELETE FROM SHOWS WHERE ID = ?");
        prep.setInt(1, id);
        prep.executeUpdate();
    }
}