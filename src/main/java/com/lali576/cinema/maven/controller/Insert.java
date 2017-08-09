package com.lali576.cinema.maven.controller;

import com.lali576.cinema.maven.exception.InsertNewShowException;
import com.lali576.cinema.maven.model.Show;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Insert {

    Connection conn = null;

    public Insert(Connection conn) {
        this.conn = conn;
    }

    public void fillUpWithRegs() {
        try (
                Scanner sc = new Scanner(new File("C:\\Users\\Tóth Ádám\\Documents\\NetBeansProjects\\cinema\\src\\hu\\elte\\wr14yr\\REGEK.txt"));) {
            while (sc.hasNextLine()) {
                String[] datas = sc.nextLine().split("; ");
                String username = datas[0];
                String password = datas[1];
                boolean isAdmin = Boolean.parseBoolean(datas[2]);

                PreparedStatement prep = conn.prepareStatement("INSERT INTO REGISTERS (USERNAME, PASSWORD, ISADMIN) VALUES (?, ?, ?)");
                prep.setString(1, username);
                prep.setString(2, password);
                prep.setBoolean(3, isAdmin);
                prep.executeUpdate();
            }
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillUpWithMovies() {
        try (
                Scanner sc = new Scanner(new File("C:\\Users\\Tóth Ádám\\Documents\\NetBeansProjects\\cinema\\src\\hu\\elte\\wr14yr\\FILMEK.txt"));) {
            while (sc.hasNextLine()) {
                String[] datas = sc.nextLine().split("; ");
                String title = datas[0];
                String country = datas[1];
                boolean isDubbed = Boolean.parseBoolean(datas[2]);
                String director = datas[3];
                String synopsis = datas[4];
                int length = Integer.parseInt(datas[5]);
                int age = Integer.parseInt(datas[6]);
                int maxPlay = Integer.parseInt(datas[7]);

                PreparedStatement prep = conn.prepareStatement("INSERT INTO MOVIES (TITLE, COUNTRY, ISDUBBED, DIRECTOR, SYNOPSIS, LENGTH)VALUES (?, ?, ?, ?, ?, ?)");
                prep.setString(1, title);
                prep.setString(2, country);
                prep.setBoolean(3, isDubbed);
                prep.setString(4, director);
                prep.setString(5, synopsis);
                prep.setInt(6, length);
                prep.executeUpdate();

                ResultSet rs = conn.createStatement().executeQuery("SELECT ID FROM MOVIES ORDER BY ID DESC");
                int movieID = 0;
                if (rs.next()) {
                    movieID = rs.getInt("ID");
                }

                prep = conn.prepareStatement("INSERT INTO AGE VALUES (?, ?)");
                prep.setInt(1, movieID);
                prep.setInt(2, age);
                prep.executeUpdate();

                prep = conn.prepareStatement("INSERT INTO SOLDTICKETS VALUES (?, ?)");
                prep.setInt(1, movieID);
                prep.setInt(2, 0);
                prep.executeUpdate();

                prep = conn.prepareStatement("INSERT INTO MAXPLAY VALUES (?, ?)");
                prep.setInt(1, movieID);
                prep.setInt(2, maxPlay);
                prep.executeUpdate();
            }
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillUpWithRooms() {
        try (
                Scanner sc = new Scanner(new File("C:\\Users\\Tóth Ádám\\Documents\\NetBeansProjects\\cinema\\src\\hu\\elte\\wr14yr\\TERMEK.txt"));) {
            while (sc.hasNextLine()) {
                String[] actLine = sc.nextLine().split("; ");
                String roomName = actLine[0];
                int roomRow = Integer.parseInt(actLine[1]);
                int roomColumn = Integer.parseInt(actLine[2]);

                PreparedStatement prep = conn.prepareStatement("INSERT INTO ROOMS (ROOMNAME, ROOMROWS, ROOMCOLUMNS) VALUES (?, ? ,?)");
                prep.setString(1, roomName);
                prep.setInt(2, roomRow);
                prep.setInt(3, roomColumn);
                prep.executeUpdate();

                ResultSet rs = conn.createStatement().executeQuery("SELECT ID FROM ROOMS ORDER BY ID DESC");
                int roomID = 0;

                if (rs.next()) {
                    roomID = rs.getInt("ID");
                }

                for (int i = 1; i <= roomRow; ++i) {
                    for (int j = 1; j <= roomColumn; ++j) {
                        prep = conn.prepareStatement("INSERT INTO SEATS (ROOMID, ROWNUMBER, COLUMNNUMBER) VALUES (?, ?, ?)");
                        prep.setInt(1, roomID);
                        prep.setInt(2, i);
                        prep.setInt(3, j);
                        prep.executeUpdate();
                    }
                }
            }
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newShow(Show show) throws InsertNewShowException {
        try {
            int movieID = show.getMovie().getID();
            int roomID = show.getRoom().getID();
            String startTime = show.getStartTime();

            PreparedStatement prep = conn.prepareStatement("INSERT INTO SHOWS (MOVIEID, ROOMID, STARTTIME) VALUES (?, ?, ?)");
            prep.setInt(1, movieID);
            prep.setInt(2, roomID);
            prep.setString(3, startTime);
            prep.executeUpdate();
        } catch(SQLException e) {
            throw new InsertNewShowException("A megadott előadás felvétele közben hiba történt!", "Adatbázis hiba!");
        }
    }
}
