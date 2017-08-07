package com.lali576.cinema.maven.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Logic {
    private Connection conn;
    String url = "jdbc:derby://localhost:1527/cinema";
    String username = "lali576";
    String password = "ember555";
    public Insert insert;
    public Select select;
    public Update update;
    public Delete delete;
    
    public Logic() throws SQLException {
        start();
        this.insert = new Insert(conn);
        this.select = new Select(conn);
        this.update = new Update(conn);
        this.delete = new Delete(conn);
    } 
    
    private void start() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        } catch(ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void stop() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch(SQLException e) {
            
        }
    }
}
