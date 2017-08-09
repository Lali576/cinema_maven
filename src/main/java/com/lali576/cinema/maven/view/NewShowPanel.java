package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.exception.CinemaException;
import com.lali576.cinema.maven.exception.InsertNewShowException;
import com.lali576.cinema.maven.exception.InvalidMovieAgeRestrictionException;
import com.lali576.cinema.maven.exception.InvalidStartTimeAndRoomException;
import com.lali576.cinema.maven.exception.InvalidStartTimeFormationException;
import com.lali576.cinema.maven.exception.InvalidStartTimeIntervalException;
import com.lali576.cinema.maven.exception.MovieCollisionException;
import com.lali576.cinema.maven.exception.MovieParallelException;
import com.lali576.cinema.maven.exception.MovieScreeningException;
import com.lali576.cinema.maven.model.Movie;
import com.lali576.cinema.maven.model.Room;
import com.lali576.cinema.maven.model.Show;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class NewShowPanel extends JPanel {
    private final Logic logic;
    public final MainFrame mainFrame;
    private List<Movie> movies;
    private List<Room> rooms;
    public JList movieList;
    public JList roomList;
    public JTextField startTimeTextField;
    
    public NewShowPanel(Logic logic, MainFrame mainFrame) {
        this.logic = logic;
        this.mainFrame = mainFrame;
        setupNewShowPanel();
    }
    
    private void setupNewShowPanel() {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton backButton = new JButton("Vissza");
        backButton.addActionListener(new GetBackToShowPanel());
        add(backButton);
        
        movies = logic.select.getMovies();
        DefaultListModel movieListElements = new DefaultListModel();
        movies.forEach((movie) -> {
            movieListElements.addElement(movie.getTitle());
        });
        
        movieList = new JList(movieListElements);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setSelectedIndex(0);
        movieList.setVisibleRowCount(5);
        
        JScrollPane scrollMovie = new JScrollPane(movieList);
        
        rooms = logic.select.getRooms();
        DefaultListModel roomListElements = new DefaultListModel();
        rooms.forEach((room) -> {
            roomListElements.addElement(room.getRoomName());
        });
        
        roomList = new JList(roomListElements);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setSelectedIndex(0);
        roomList.setVisibleRowCount(5);
        
        JScrollPane scrollRoom = new JScrollPane(roomList);
        
        add(scrollMovie);
        add(scrollRoom);
        
        JLabel startTimeLabel = new JLabel("Kezdeti időpont:", JLabel.TRAILING);
        add(startTimeLabel);
        startTimeTextField = new JTextField(5);
        startTimeLabel.setLabelFor(startTimeTextField);
        add(startTimeTextField);
        JButton submit = new JButton("Új előadás");
        submit.addActionListener(new InsertNewShow());
        add(submit);
    }
    
    private class GetBackToShowPanel implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {           
            mainFrame.getShowPanelByNewShowPanel();
        } 
    }
    
    private class InsertNewShow implements ActionListener {
       @Override
        public void actionPerformed(ActionEvent e) {
            String movieTitle = movieList.getSelectedValue().toString();
            String roomName = roomList.getSelectedValue().toString();
            
            Movie selectedMovie = null;            
            for(Movie movie : movies) {
                if(movieTitle.equals(movie.getTitle())){
                    selectedMovie = movie;
                    break;
                }
            }
            
            Room selectedRoom = null;           
            for(Room room : rooms) {
                if(roomName.equals(room.getRoomName())){
                    selectedRoom = room;
                    break;
                }
            }
            
            String startTime = startTimeTextField.getText();
            
            try {
                if(isAllRight(logic, selectedMovie, selectedRoom, startTime)) {
                    Show show = Show.createShow(selectedMovie, selectedRoom, startTime);
                    logic.insert.newShow(show);
                    mainFrame.getShowPanelByNewShowPanel();
                }
            } catch(InsertNewShowException ex) {
                ex.writeOutEception();
            }            
        } 
    }
    
    public static boolean isAllRight(Logic logic, Movie movie, Room room, String startTime) {
        try {
            return (isShowUnique(movie, room, startTime) && startTimeRegExp(startTime) && movieParallel(movie, startTime) && moviePlayNumber(movie) && rightSchedule(movie, room, startTime) && movieAge(movie, startTime));        
        } catch(CinemaException ex) {
            ex.writeOutEception();
        }
        
        return false;
    }
    
    private static boolean isShowUnique(Movie movie, Room room, String startTime) throws InvalidStartTimeAndRoomException {
        for(Show show : room.getShows()) {
            if(show.getStartTime().equals(startTime)) {
                throw new InvalidStartTimeAndRoomException("A megadott terem és időpont már létezik!", "Hiba!");
            }
        }
        
        return true;
    }
    
    private static boolean startTimeRegExp(String startTime) throws InvalidStartTimeFormationException, InvalidStartTimeIntervalException {
        String regexp = "[0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}";
        if(!Pattern.compile(regexp).matcher(startTime).find()) {
            throw new InvalidStartTimeFormationException("A megadott időpont nem jó formátumú!", "Hiba!");
        }
        
        int hour = Integer.parseInt(startTime.split(":")[0]);
        if(!(hour >= 8 && hour <= 21)) {
            throw new InvalidStartTimeIntervalException("A megadott időpont nem jó intervallumban van!", "Hiba!");
        }
        
        return true;
    }
    
    private static boolean movieParallel(Movie movie, String startTime) throws MovieParallelException {
        int n = 0;
        n = movie.getShows().stream().filter((show) -> (show.getStartTime().equals(startTime))).map((_item) -> 1).reduce(n, Integer::sum);
        
        if(n >= 3) {
            throw new MovieParallelException("A megadott film túlépte a párhuzamosági szintet!", "Hiba!");
        }
        
        return true;
    }
    
    private static boolean moviePlayNumber(Movie movie) throws MovieScreeningException{
        if(movie.getShows().size() == movie.getMaxPlay()) {
            throw new MovieScreeningException("A megadott film túlépte a vetítési keretjét!", "Hiba!");
        }
        
        return true;
    }
    
    private static boolean rightSchedule(Movie movie, Room room, String startTime) throws MovieCollisionException {
        for(Show show : room.getShows()) {
            String endTime1 = plusThirtyMinutes(show.getStartTime(), show.getMovie().getLength());
            String endTime2 = plusThirtyMinutes(startTime, movie.getLength());
            
            if(isCrossed(startTime, show.getStartTime())) {
                if(isCrossed(endTime1, startTime)) {
                    throw new MovieCollisionException("A megadott film ütközik egy másik filmmel!", "Hiba!");
                }
            } else {
                if(isCrossed(show.getStartTime(), endTime2)) {
                    throw new MovieCollisionException("A megadott film ütközik egy másik filmmel!", "Hiba!");
                }
            }
        }
        return true;
    }
    
    private static boolean isCrossed(String endTime, String startTime) {
        String[] endDatas = endTime.split(":");
        String[] startDatas = startTime.split(":");
        int endHour = Integer.parseInt(endDatas[0]);
        int endMinute = Integer.parseInt(endDatas[1]);
        int startHour = Integer.parseInt(startDatas[0]);
        int startMinute = Integer.parseInt(startDatas[1]);
        
        if(endHour > startHour) {
            return false;
        } else if(endHour == startHour) {
            if(endMinute > startMinute) {
                return false;
            } else if(endMinute <= startMinute) {
                return true;
            }
        } else if(endHour < startHour) {
            return true;
        }
        
        return false;
    }
    
    private static String plusThirtyMinutes(String startTime, int length) {        
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1]);
        int endHour = startHour + length/60;
        int endMinute = startMinute + length%60;
        
        if(endMinute > 60) {
                int sv = endMinute-60;
                endHour++;
                endMinute = sv;
        } else if(endMinute == 60) {
                endHour++;
                endMinute = 0;
        }
        
        endMinute += 30;
        
        if(endMinute > 60) {
                int sv = endMinute-60;
                endHour++;
                endMinute = sv;
        } else if(endMinute == 60) {
                endHour++;
                endMinute = 0;
        }
        String endTime = endHour + ":" + endMinute;
        String ehs = (endHour < 10) ? "0" + endHour : Integer.toString(endHour);
        String ems = (endMinute < 10) ? "0" + endMinute : Integer.toString(endMinute);
        return ehs + ":" + ems;
    }
    
    private static boolean movieAge(Movie movie, String startTime) throws InvalidMovieAgeRestrictionException {
        int hour = Integer.parseInt(startTime.split(":")[0]);
        
        if(!((movie.getAge() == 4 && hour >= 17) || (movie.getAge() == 5 && hour >= 21 || movie.getAge() <= 3))) {
            throw new InvalidMovieAgeRestrictionException("A megadott film korhatára nem megfelelő az időponthoz!", "Hiba!");
        }
        
        return true;
    }
}