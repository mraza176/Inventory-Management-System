package pl.login;

import bll.BusinessLogic;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import pl.PresentationLayer;
import pl.login.manager.FormsManager;
import raven.toast.*;

public class Login extends JPanel implements ActionListener {

    private JTextField userField;
    private JPasswordField passField;
    private JCheckBox remMeCheckBox;
    private JButton loginButton;

    private BusinessLogic bll;

    public Login(BusinessLogic bll) {

        this.bll = bll;

        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        userField = new JTextField();
        passField = new JPasswordField();
        remMeCheckBox = new JCheckBox("Remember Me");
        loginButton = new JButton("Login");
        loginButton.setFocusable(true);

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");

        userField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");

        passField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        passField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true");

        loginButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        JLabel titleLabel = new JLabel("Welcome Back!");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");

        JLabel descLabel = new JLabel("Please sign in to access your account");
        descLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        panel.add(titleLabel);
        panel.add(descLabel);
        panel.add(new JLabel("Username"), "gapy 8");
        panel.add(userField);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(passField);
        panel.add(remMeCheckBox, "grow 0");
        panel.add(loginButton, "gapy 10");
        panel.add(createSignupLabel(), "gapy 10");

        add(panel);

        setBounds(0, 0, 1080, 695);

        loginButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        loginButton.getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });

        loginButton.addActionListener(this);
    }

    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        JButton registerButton = new JButton("<html><a href=\"#\">Sign up</a></html>");
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,3,3,3");
        registerButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Register(bll));
        });

        JLabel label = new JLabel("Don't have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        panel.add(label);
        panel.add(registerButton);

        return panel;
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            if (userField.getText().equals("admin") && getPassword().equals("admin")) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Logged in as Admin");
                PresentationLayer.getInstance().setDrawerBuilder("admin");
                return;
            }
            HashMap<String, String> user = bll.getUser();
            for (Map.Entry<String, String> u : user.entrySet()) {
                if (userField.getText().equals(u.getKey()) && getPassword().equals(u.getValue())) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Logged in as " + bll.getUserName(userField.getText()));
                    PresentationLayer.getInstance().setDrawerBuilder("user", userField.getText());
                    return;
                }
            }
            Notifications.getInstance().show(Notifications.Type.ERROR, "Invalid Username or Password");
        }
    }
}
