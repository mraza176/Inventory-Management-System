package pl;

import bll.BusinessLogic;
import com.formdev.flatlaf.extras.*;
import java.awt.*;
import pl.login.Login;
import javax.swing.*;
import pl.accounts.*;
import pl.login.manager.FormsManager;
import raven.drawer.Drawer;
import raven.popup.GlassPanePopup;
import raven.toast.*;

public class PresentationLayer extends JFrame {

    private static PresentationLayer instance;

    private BusinessLogic bll;

    public PresentationLayer(BusinessLogic bll) {

        this.bll = bll;

        GlassPanePopup.install(this);

        add(new Login(bll));
        FormsManager.getInstance().initApplication(this);
        
        Notifications.getInstance().setJFrame(this);

        setSize(1080, 720);
        setMinimumSize(new Dimension(1080, 720));
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static PresentationLayer getInstance() {
        if (instance == null) {
            instance = new PresentationLayer(BusinessLogic.getInstance());
        }
        return instance;
    }

    public void setDrawerBuilder(String accType) {
        Drawer.getInstance().setDrawerBuilder(new AdminLogin());
        FlatAnimatedLafChange.showSnapshot();
        setContentPane(AdminPanel.getInstance());
        revalidate();
        repaint();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public void setDrawerBuilder(String accType, String username) {
        UserPanel.getInstance().updateProductsPanel(username);
        Drawer.getInstance().setDrawerBuilder(new UserLogin());
        FlatAnimatedLafChange.showSnapshot();
        setContentPane(UserPanel.getInstance());
        revalidate();
        repaint();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
}
