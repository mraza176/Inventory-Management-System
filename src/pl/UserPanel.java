package pl;

import bll.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.*;
import com.formdev.flatlaf.themes.*;
import javax.swing.table.*;
import pl.login.Login;
import raven.alerts.*;
import raven.drawer.*;
import raven.toast.*;

public class UserPanel extends JPanel implements ActionListener {

    private static UserPanel instance;

    private BusinessLogic bll;

    private JPanel userPanel1, userPanel2;
    private JButton menuButton, modeButton, logoutButton;

    private JPanel productsPanel;
    private JScrollPane productsScrollPane;

    private JLabel cartItemLabel, emptyCartLabel;
    private JTable cartItemsTable;
    private DefaultTableModel cartItemsModel;
    private JScrollPane cartScrollPane;
    private JButton removeItemButton, confirmOrderButton;

    private JLabel orderLabel;
    private DefaultTableModel orderTableModel;
    private JTable orderTable;
    private JScrollPane orderScrollPane;

    public String username;

    public UserPanel(BusinessLogic bll) {

        this.bll = bll;

        menuButton = new JButton();
        menuButton.setBounds(20, 10, 45, 45);
        menuButton.setIcon(new FlatSVGIcon("pl/icons/menu.svg", 25, 25));
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0;"
                + "innerFocusWidth:0;");

        modeButton = new JButton();
        modeButton.setBounds(20, 75, 45, 45);
        modeButton.setIcon(new FlatSVGIcon("pl/icons/dark.svg", 25, 25));
        modeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        modeButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0;"
                + "innerFocusWidth:0;");

        logoutButton = new JButton();
        logoutButton.setBounds(20, 600, 45, 45);
        logoutButton.setIcon(new FlatSVGIcon("pl/icons/logout.svg", 25, 25));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0;"
                + "innerFocusWidth:0;");

        userPanel1 = new JPanel(null);
        userPanel1.setBounds(0, 0, 85, 720);
        userPanel1.setBackground(new Color(50, 50, 50));

        userPanel1.add(menuButton);
        userPanel1.add(modeButton);
        userPanel1.add(logoutButton);

        userPanel2 = new JPanel(null);
        userPanel2.setBounds(85, 0, 995, 695);

        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        productsPanel.setBorder(BorderFactory.createEtchedBorder());

        for (String inventory : bll.getInventory()) {
            productsPanel.add(new ProductsPanel(inventory));
        }

        productsScrollPane = new JScrollPane(productsPanel);
        productsScrollPane.setWheelScrollingEnabled(true);
        productsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        productsScrollPane.setBounds(35, 35, 910, 620);

        userPanel2.add(productsScrollPane);

        emptyCartLabel = new JLabel("Your Cart Is Empty!");
        emptyCartLabel.setBounds(75, 75, 300, 36);
        emptyCartLabel.setFont(new Font("Arial", Font.BOLD, 28));

        cartItemLabel = new JLabel("Cart Items");
        cartItemLabel.setBounds(75, 50, 180, 36);
        cartItemLabel.setFont(new Font("Arial", Font.PLAIN, 28));

        cartItemsModel = new DefaultTableModel();
        cartItemsModel.addColumn("Product Name");
        cartItemsModel.addColumn("Product Description");
        cartItemsModel.addColumn("Product Price");

        cartItemsTable = new JTable(cartItemsModel);

        cartScrollPane = new JScrollPane(cartItemsTable);
        cartScrollPane.setBounds(75, 110, 830, 300);

        confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setBounds(800, 50, 105, 36);
        confirmOrderButton.setFont(new Font("Arial", Font.PLAIN, 14));

        removeItemButton = new JButton("Remove Item");
        removeItemButton.setBounds(420, 450, 100, 36);
        removeItemButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeItemButton.setEnabled(false);
        
        orderLabel = new JLabel("Order History");
        orderLabel.setBounds(75, 50, 180, 36);
        orderLabel.setFont(new Font("Arial", Font.PLAIN, 28));

        add(userPanel1);
        add(userPanel2);

        menuButton.addActionListener(this);
        modeButton.addActionListener(this);
        logoutButton.addActionListener(this);

        cartItemsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeItemButton.setEnabled(true);
            }
        });

        removeItemButton.addActionListener(this);
        confirmOrderButton.addActionListener(this);

        setLayout(null);
        setBounds(0, 0, 1080, 720);
    }

    public static UserPanel getInstance() {
        if (instance == null) {
            instance = new UserPanel(BusinessLogic.getInstance());
        }
        return instance;
    }

    public void menuActions(int menu, int subMenu) {
        if (menu == 0) {
            updateProductsPanel(username);

            userPanel2.removeAll();
            userPanel2.add(productsScrollPane);
            userPanel2.repaint();

            Drawer.getInstance().closeDrawer();
        }
        if (menu == 1) {
            confirmOrderButton.setEnabled(true);
            userPanel2.removeAll();

            if (cartItemsTable.getRowCount() != 0) {
                userPanel2.add(cartItemLabel);
                userPanel2.add(cartScrollPane);
                userPanel2.add(confirmOrderButton);
                userPanel2.add(removeItemButton);
            } else {
                userPanel2.add(emptyCartLabel);
            }

            userPanel2.repaint();

            Drawer.getInstance().closeDrawer();
        }
        if (menu == 2) {
            
            orderTableModel = new DefaultTableModel();
            orderTableModel.addColumn("Product Name");
            orderTableModel.addColumn("Product Description");
            orderTableModel.addColumn("Product Price");
            
            orderTable = new JTable(orderTableModel);
            
            orderScrollPane = new JScrollPane(orderTable);
            orderScrollPane.setBounds(75, 110, 830, 400);
            
            orderTableModel = bll.getOrders(username, orderTableModel);
            
            userPanel2.removeAll();

            userPanel2.add(orderLabel);
            userPanel2.add(orderScrollPane);
            
            userPanel2.repaint();
            
            Drawer.getInstance().closeDrawer();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            Drawer.getInstance().showDrawer();
        }
        if (e.getSource() == modeButton) {
            if (!FlatLaf.isLafDark()) {
                FlatAnimatedLafChange.showSnapshot();
                modeButton.setIcon(new FlatSVGIcon("pl/icons/dark.svg", 25, 25));
                FlatMacDarkLaf.setup();
                menuButton.setBackground(new Color(90, 90, 90));
                modeButton.setBackground(new Color(90, 90, 90));
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                userPanel1.setBackground(new Color(50, 50, 50));
                userPanel1.repaint();
            } else {
                FlatAnimatedLafChange.showSnapshot();
                FlatMacLightLaf.setup();
                menuButton.setIcon(new FlatSVGIcon("pl/icons/menuLight.svg", 25, 25));
                menuButton.setBackground(new Color(0, 122, 255));
                modeButton.setIcon(new FlatSVGIcon("pl/icons/light.svg", 25, 25));
                modeButton.setBackground(new Color(0, 122, 255));
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                userPanel1.setBackground(new Color(220, 235, 250));
                userPanel1.repaint();
            }
        }
        if (e.getSource() == logoutButton) {
            userPanel2.removeAll();
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Logout Successfully");
            FlatAnimatedLafChange.showSnapshot();
            PresentationLayer.getInstance().setContentPane(new Login(bll));
            PresentationLayer.getInstance().revalidate();
            PresentationLayer.getInstance().repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }
        if (e.getSource() == removeItemButton) {
            bll.updateRemovePQuantity((String) cartItemsModel.getValueAt(cartItemsTable.getSelectedRow(), 0));
            cartItemsModel.removeRow(cartItemsTable.getSelectedRow());
            removeItemButton.setEnabled(false);
            Notifications.getInstance().show(Notifications.Type.INFO, "Product Removed Successfully");
            if (cartItemsTable.getRowCount() == 0) {
                confirmOrderButton.setEnabled(false);
            }
        }
        if (e.getSource() == confirmOrderButton) {
            int count = cartItemsModel.getRowCount();
            for (int i = 0; i < cartItemsModel.getRowCount(); i++) {
                bll.addOrders(username, (String) cartItemsModel.getValueAt(i, 0), (String) cartItemsModel.getValueAt(i, 1), (String) cartItemsModel.getValueAt(i, 2));
            }
            userPanel2.removeAll();
            MessageAlerts.getInstance().showMessage("Order Confirmed!", "Your order will be delivered within 3 to 4 working days", MessageAlerts.MessageType.SUCCESS);
            for (int i = count - 1; i >= 0; i--) {
                cartItemsModel.removeRow(i);
            }
        }
    }

    public void cartItems(String pName) {
        bll.updateAddPQuantity(pName);
        String[] items = {pName, bll.getPDesc(pName), bll.getPPrice(pName) + ""};
        cartItemsModel.addRow(items);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Product Added To Cart Successfully");
    }

    public void updateProductsPanel(String username) {
        this.username = username;
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        productsPanel.setBorder(BorderFactory.createEtchedBorder());

        for (String inventory : bll.getInventory()) {
            productsPanel.add(new ProductsPanel(inventory));
        }

        productsScrollPane = new JScrollPane(productsPanel);
        productsScrollPane.setWheelScrollingEnabled(true);
        productsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        productsScrollPane.setBounds(35, 35, 910, 620);
    }
}
