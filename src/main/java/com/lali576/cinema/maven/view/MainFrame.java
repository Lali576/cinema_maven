package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.model.Movie;
import com.lali576.cinema.maven.model.Register;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Show;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SpringLayout;

public final class MainFrame extends JFrame{
    private final Logic logic;
    private RegSignInPanel regSignInPanel;
    private MoviePanel moviePanel;
    private NewShowPanel newShowPanel;
    private DeleteShowPanel deleteShowPanel;
    private RoomPanel roomPanel;
    private ShowPanel showPanel;
    private JMenuBar menuBar;
    
    public MainFrame() throws SQLException {
        logic = new Logic();
        setFrameProperties();
        getRegSignInPanel();
        menuBar = null;
    }
    
    //regisztracios lap betoltese
    private void getRegSignInPanel() {
        regSignInPanel = new RegSignInPanel(logic, this);
        add(regSignInPanel);
        revalidate();
        repaint();
    }
    
    //eloadas oldal betoltese regisztracio utan
    public void getShowPanelByRegisterPanel(Register reg) {
        remove(regSignInPanel);
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Műveletek");
        if(reg.getIsAdmin()) {
            menu.add("Új előadás").addActionListener(new GetNewShowPanel());            
            menu.add("Előadás törlése").addActionListener(new GetDeleteShowPanel());
        }
        menu.add("Kijelentkezés").addActionListener(new LogoutReg());
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        getShowPanel();
    }
    
    //uj eloadas oldal betoltese
    public void getNewShowPanel() {
        try {
                remove(showPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(roomPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(moviePanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(deleteShowPanel);
            } catch(Exception ex) {
                
            }
        newShowPanel = new NewShowPanel(logic, this);
        add(newShowPanel);
        revalidate();
        repaint();
    }
    
    //eloadas torles oldalanak betoltese
    public void getDeleteShowPanel() {
        try {
                remove(showPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(roomPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(moviePanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(newShowPanel);
            } catch(Exception ex) {
                
            }
            
        deleteShowPanel = new DeleteShowPanel(logic, this);
        add(deleteShowPanel);
        revalidate();
        repaint();
    }
    
    //
    public void getShowPanelByNewShowPanel() {
        remove(newShowPanel);
        getShowPanel();
    }
    
    public void getShowPanelByDeleteShowPanel() {
        remove(deleteShowPanel);
        getShowPanel();
    }
    
    public void getShowPanelByMoviePanel() {
        remove(moviePanel);
        getShowPanel();
    }
    
    public void getShowPanelByRoomPanel() {
        remove(roomPanel);
        getShowPanel();
    }
    
    private void getShowPanel() {       
        showPanel = new ShowPanel(logic, this);
        add(showPanel);
        revalidate();
        repaint();
    }
    
    public void getMoviePanel(Movie movie) {
        remove(showPanel);
        moviePanel = new MoviePanel(this, movie);
        add(moviePanel);
        revalidate();
        repaint();
    }
    
    public void getRoomPanel(Room room, Show show) {
        remove(showPanel);
        roomPanel = new RoomPanel(logic, room, show, this);
        add(roomPanel);
        revalidate();
        repaint();
    }
    
    private void setFrameProperties() {
        setTitle("MoziPenztar");
        setDefaultCloseOperation();
        setLocation(300, 300);
        setSize(1000, 500);
        setLayout(new SpringLayout());
        setResizable(false);
    }
    
    private void setDefaultCloseOperation() {
        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logic.stop();
                super.windowClosing(e);
                System.exit(0);
            }           
        };
        
        this.addWindowListener(windowListener);
    }
    
    private class GetNewShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            getNewShowPanel();
        } 
    }
    
    private class GetDeleteShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            getDeleteShowPanel();
        } 
    }
    
    private class LogoutReg implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            try {
                remove(showPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(roomPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(moviePanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(newShowPanel);
            } catch(Exception ex) {
                
            }
            
            try {
                remove(deleteShowPanel);
            } catch(Exception ex) {
                
            }
            
            setJMenuBar(null);
            getRegSignInPanel();
        } 
    }
}