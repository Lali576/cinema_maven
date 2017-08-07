package com.lali576.cinema.maven.view;

import javax.swing.JPanel;
import com.lali576.cinema.maven.model.Movie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MoviePanel extends JPanel {
    private final MainFrame mainFrame;
    private final Movie movie;
    
    public MoviePanel(MainFrame mainFrame, Movie movie) {
        this.mainFrame = mainFrame;
        this.movie = movie;
        setupMoviePanel();
    }
    
    private void setupMoviePanel() {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new GetBackToShowPanel());
        add(backButton);
        JLabel titleLabel = new JLabel("Cim: " + movie.getTitle());
        add(titleLabel);
        JLabel countryLabel = new JLabel("Orszag: " + movie.getCountry());
        add(countryLabel);
        JLabel isDubbedLabel = new JLabel("Szinkronizalt: " + (movie.getIsDubbed() ? "igen" : "nem") );
        add(isDubbedLabel);
        JLabel directorLabel = new JLabel("Rendezo: " + movie.getDirector());
        add(directorLabel);
        JLabel synopsisLabel = new JLabel("Leiras: " + movie.getSynopsis());
        add(synopsisLabel);
        JLabel lengthLabel = new JLabel("Hossz: " + movie.getLength()+ " perc");
        add(lengthLabel);
        JLabel soldTicketsLabel = new JLabel("Eladott jegyek: " + movie.getSoldTickets());
        add(soldTicketsLabel);
        revalidate();
        repaint();
    }
    
    private class GetBackToShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            mainFrame.getShowPanelByMoviePanel();
        } 
    }
}
