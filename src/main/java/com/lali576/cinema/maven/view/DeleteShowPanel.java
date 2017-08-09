package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.exception.InvalidShowDeletionException;
import com.lali576.cinema.maven.exception.ShowDatabaseException;
import com.lali576.cinema.maven.model.Show;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DeleteShowPanel extends JPanel {
    private final Logic logic;
    public MainFrame mainFrame;
    public List<Show> shows;
    
    public DeleteShowPanel(Logic logic, MainFrame mainFrame) {
        this.logic = logic;
        this.mainFrame = mainFrame;
        shows = logic.select.getShows();
        setupDeleteShowPanel();
    }
    
    private void setupDeleteShowPanel() {
        removeAll();
        setLayout(new BorderLayout());
        JButton button = new JButton("Vissza");
        button.addActionListener(new GetBackToShowPanel());
        add(button, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(shows.size(), 4));
        shows.stream().map((show) -> {
            JLabel timeLabel = new JLabel(show.getStartTime());
            panel.add(timeLabel);
            JLabel titleLabel = new JLabel(show.getMovie().getTitle());
            panel.add(titleLabel);
            JLabel roomNameLabel = new JLabel(show.getRoom().getRoomName());
            panel.add(roomNameLabel);
            JButton deleteButton = new JButton("X");
            deleteButton.addActionListener(new DeleteShow(show));
            return deleteButton;
        }).forEachOrdered((deleteButton) -> {
            panel.add(deleteButton);
        });
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private class DeleteShow implements ActionListener {
        private final Show show;
        
        public DeleteShow(Show show) {
            this.show = show;
        }
        
       @Override
        public void actionPerformed(ActionEvent e) {   
            try{
                logic.delete.deleteShow(show);
                shows = logic.select.getShows();
                setupDeleteShowPanel();
            } catch(InvalidShowDeletionException | ShowDatabaseException ex) {
                ex.writeOutEception();
            }
        } 
    }
    
    private class GetBackToShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            mainFrame.getShowPanelByDeleteShowPanel();
        } 
    }
}
