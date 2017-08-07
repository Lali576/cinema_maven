package com.lali576.cinema.maven.view;

import com.lali576.cinema.maven.controller.Logic;
import com.lali576.cinema.maven.model.Register;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegSignInPanel extends JPanel {
    private final Logic logic;
    private final MainFrame mainFrame;
    
    public RegSignInPanel(Logic logic, MainFrame mainFrame) {
        this.logic = logic;
        this.mainFrame = mainFrame;
        setupRegSignInPanel();
    }

    //registracios oldal beallitasa
    private void setupRegSignInPanel(){
        removeAll();
        setLayout(new GridLayout(3, 1));
        JLabel usernameLabel = new JLabel("Felhasználó név:", JLabel.TRAILING);
        add(usernameLabel);
        JTextField usernameTextField = new JTextField(10);
        usernameLabel.setLabelFor(usernameTextField);
        add(usernameTextField);
        JLabel passwordLabel = new JLabel("Jelszó:", JLabel.TRAILING);
        add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(10);
        passwordLabel.setLabelFor(passwordField);
        add(passwordField);
        JButton submit = new JButton("Bejelentkezés");
        submit.addActionListener(new SignInRegister());
        add(submit);
        revalidate();
        repaint();
    }
    
    //bejelentkezesi gomb
    private class SignInRegister implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton)e.getSource();
            JTextField usernameTextField = (JTextField)btn.getParent().getComponent(1);
            String username = usernameTextField.getText();
            JPasswordField passwordField = (JPasswordField)btn.getParent().getComponent(3);
            String password = new String(passwordField.getPassword());
    
            Register reg = logic.select.getRegister(username, password);
            if(reg != null) {
                mainFrame.getShowPanelByRegisterPanel(reg);
            } else {
                JOptionPane.showMessageDialog(null, "Hibás felhasználónév vagy jelszó!", "Bejelentkezési hiba!", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
    }
}
