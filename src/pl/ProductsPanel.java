package pl;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.awt.event.*;
import raven.toast.*;

public class ProductsPanel extends JPanel implements ActionListener {

    private JLabel imageLabel, pNameLabel, pPriceLabel;
    private JButton addToCartButton;

    public ProductsPanel(String pName) {

        imageLabel = new JLabel(bll.BusinessLogic.getInstance().getPImage(pName));
        imageLabel.setBounds(44, 12, 200, 200);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.getColor(TOOL_TIP_TEXT_KEY), 2));

        pNameLabel = new JLabel(pName);
        pNameLabel.setBounds(18, 220, 288, 36);
        pNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        pPriceLabel = new JLabel("RS " + bll.BusinessLogic.getInstance().getPPrice(pName));
        pPriceLabel.setBounds(18, 260, 170, 36);
        pPriceLabel.setFont(new Font("Arial", Font.BOLD, 22));

        addToCartButton = new JButton("Add To Cart");
        addToCartButton.setBounds(180, 255, 90, 36);
        addToCartButton.setFont(new Font("Arial", Font.PLAIN, 14));

        add(imageLabel);
        add(pNameLabel);
        add(pPriceLabel);
        add(addToCartButton);

        setPreferredSize(new Dimension(288, 300));
        setBackground(new Color(50, 50, 50));
        setLayout(null);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20");

        addToCartButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToCartButton) {
            if (bll.BusinessLogic.getInstance().getPQuantity(pNameLabel.getText()) > 0) {
                UserPanel.getInstance().cartItems(pNameLabel.getText());
            } else {
                addToCartButton.setEnabled(false);
                Notifications.getInstance().show(Notifications.Type.ERROR, "This product is no longer available");
            }
        }
    }
}
