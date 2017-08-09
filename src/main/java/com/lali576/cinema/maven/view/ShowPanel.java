package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.exception.ShowDatabaseException;
import com.lali576.cinema.maven.exception.ShowsNotFoundByMovieTitleException;
import com.lali576.cinema.maven.exception.ShowsNotFoundByRoomNameException;
import com.lali576.cinema.maven.model.Movie;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Show;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShowPanel extends JPanel {

    private final Logic logic;
    private List<Show> shows;
    private final MainFrame mainFrame;
    public JTextField titleTextField;
    public JTextField roomNameTextField;

    public ShowPanel(Logic logic, MainFrame mainFrame) {
        this.logic = logic;
        this.mainFrame = mainFrame;
        shows = logic.select.getShows();
        setupShowsPanel();
    }

    private void setupShowsPanel() {
        removeAll();
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        JLabel titleLabel = new JLabel("Film címe:", JLabel.TRAILING);
        northPanel.add(titleLabel);
        titleTextField = new JTextField(30);
        titleLabel.setLabelFor(titleTextField);
        northPanel.add(titleTextField);
        JButton sendTitle = new JButton("Küld");
        sendTitle.addActionListener(new GetShowsByMovieTitle());
        northPanel.add(sendTitle);
        JLabel nameLabel = new JLabel("Terem neve:", JLabel.TRAILING);
        northPanel.add(nameLabel);
        roomNameTextField = new JTextField(30);
        nameLabel.setLabelFor(roomNameTextField);
        northPanel.add(roomNameTextField);
        JButton sendName = new JButton("Küld");
        sendName.addActionListener(new GetShowsByRoomName());
        northPanel.add(sendName);
        add(northPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(shows.size(), 5));
        Collections.sort(shows);
        shows.stream().map((show) -> {
            JLabel timeLabel = new JLabel(show.getStartTime());
            centerPanel.add(timeLabel);
            MovieButton movieButton = new MovieButton(show.getMovie());
            movieButton.addActionListener(new GetMovieDetails());
            centerPanel.add(movieButton);
            JLabel roomNameLabel = new JLabel(show.getRoom().getRoomName());
            centerPanel.add(roomNameLabel);
            RoomButton button = new RoomButton(show.getRoom(), show);
            return button;
        }).map((button) -> {
            button.addActionListener(new GetRoomDetails());
            return button;
        }).forEachOrdered((button) -> {
            centerPanel.add(button);
        });
        add(centerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private class GetShowsByMovieTitle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleTextField.getText();
            if (!title.trim().equals("")) {
                try {
                    List<Show> selectedShows = logic.select.getShowsByMovieTitle(title);
                    shows = selectedShows;
                    setupShowsPanel();
                } catch(ShowDatabaseException ex) {
                    ex.writeOutEception();
                }
            }
        }
    }

    private class GetShowsByRoomName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = roomNameTextField.getText();
            if (!roomName.trim().equals("")) {
                try {
                    List<Show> selectedShows = logic.select.getShowsByRoomName(roomName);
                    shows = selectedShows;
                    setupShowsPanel();
                } catch (ShowsNotFoundByRoomNameException ex) {
                    ex.writeOutEception();
                }
            }
        }
    }

    private class GetMovieDetails implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MovieButton btn = (MovieButton) e.getSource();
            Movie movie = btn.getMovie();

            mainFrame.getMoviePanel(movie);
        }
    }

    private class GetRoomDetails implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RoomButton btn = (RoomButton) e.getSource();
            Room room = btn.getRoom();
            Show show = btn.getShow();

            mainFrame.getRoomPanel(room, show);
        }
    }
}
