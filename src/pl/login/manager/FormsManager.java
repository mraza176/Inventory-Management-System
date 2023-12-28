package pl.login.manager;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import javax.swing.*;
import java.awt.*;
import pl.PresentationLayer;

public class FormsManager {

    private PresentationLayer pl;

    private static FormsManager instance;

    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    public void initApplication(PresentationLayer pl) {
        this.pl = pl;
    }

    public void showForm(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            pl.setContentPane(form);
            pl.revalidate();
            pl.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
