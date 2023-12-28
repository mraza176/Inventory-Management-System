package pl.login;

import bll.BusinessLogic;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;
import pl.login.manager.FormsManager;
import pl.login.password.PasswordStrengthStatus;
import raven.alerts.MessageAlerts;

public class Register extends JPanel implements ActionListener {

    private JTextField firstNameField, lastNameField, userField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JPasswordField passwordField, confirmPasswordField;
    private PasswordStrengthStatus passwordStrengthStatus;
    private ButtonGroup groupGender;
    private JButton registerButton;

    private BusinessLogic bll;

    public Register(BusinessLogic bll) {

        this.bll = bll;

        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        userField = new JTextField();
        passwordField = new JPasswordField();
        passwordStrengthStatus = new PasswordStrengthStatus();
        confirmPasswordField = new JPasswordField();
        registerButton = new JButton("Sign up");

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");

        firstNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First name");
        lastNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Last name");
        userField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username or email");
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-enter your password");

        passwordField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true");

        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true");

        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        JLabel titleLabel = new JLabel("Welcome to our Inventory Management System!");
        JLabel descLabel = new JLabel("Fill Required Fields to Create Your Account");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        descLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        passwordStrengthStatus.initPasswordField(passwordField);

        panel.add(titleLabel);
        panel.add(descLabel);
        panel.add(new JLabel("Full Name"), "gapy 10");
        panel.add(firstNameField, "split 2");
        panel.add(lastNameField);
        panel.add(new JLabel("Gender"), "gapy 8");
        panel.add(createGenderPanel());
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Username or Email"));
        panel.add(userField);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(passwordField);
        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Confirm Password"), "gapy 0");
        panel.add(confirmPasswordField);
        panel.add(registerButton, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");

        add(panel);

        registerButton.addActionListener(this);
    }

    private Component createGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        groupGender = new ButtonGroup();
        groupGender.add(maleRadioButton);
        groupGender.add(femaleRadioButton);
        maleRadioButton.setSelected(true);
        panel.add(maleRadioButton);
        panel.add(femaleRadioButton);

        return panel;
    }

    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JButton logiButton = new JButton("<html><a href=\"#\">Sign in here</a></html>");
        logiButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,3,3,3");
        logiButton.setContentAreaFilled(false);
        logiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logiButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Login(bll));
        });
        JLabel label = new JLabel("Already have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(logiButton);
        return panel;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getGender() {
        if (maleRadioButton.isSelected()) {
            return maleRadioButton.getText();
        } else {
            return femaleRadioButton.getText();
        }
    }

    public String getUserName() {
        return userField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            if (bll.registerUser(getFirstName(), getLastName(), getGender(), getUserName(), getPassword())) {
                MessageAlerts.getInstance().showMessage("Successful!", "You are Successfully Registered", MessageAlerts.MessageType.SUCCESS);
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is some error on our end. Try again later", MessageAlerts.MessageType.ERROR);
            }
        }
    }
}
