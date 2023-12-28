package pl;

import bll.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.imageio.*;
import java.io.*;
import java.util.logging.*;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.*;
import com.formdev.flatlaf.themes.*;
import pl.login.*;
import raven.alerts.MessageAlerts;
import raven.drawer.Drawer;

public class AdminPanel extends JPanel implements ActionListener {

    private static AdminPanel instance;

    private BusinessLogic bll;

    private JPanel adminPanel1, adminPanel2;
    private JButton menuButton, modeButton, logoutButton;

//  Category Graphics
    private JButton addCatButton, delCatButton;
    private JTextField addCatField;
    private JLabel addCatLabel, delCatLabel;

//  Product Graphics
    private JLabel pNameLabel, pDescLabel, pCatLabel, pPriceLabel, pQuantityLabel, pAddImageLabel, pImageLabel, selectPLabel;
    private JTextField pNameField, pDescField, pPriceField, pQuantityField;
    private JButton addProdButton, addImageButton, updatePButton, delPButton;

//  Revenue Graphics
    private JLabel salesLabel, revenueLabel;
    private DefaultTableModel salesTableModel;
    private JTable salesTable;
    private JScrollPane salesScrollPane;
    private JButton reportButton;

    private JComboBox proComboBox, catComboBox;
    private String imagePath;

    public AdminPanel(BusinessLogic bll) {

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

        adminPanel1 = new JPanel(null);
        adminPanel1.setBounds(0, 0, 85, 720);
        adminPanel1.setBackground(new Color(50, 50, 50));

        adminPanel1.add(menuButton);
        adminPanel1.add(modeButton);
        adminPanel1.add(logoutButton);

        adminPanel2 = new JPanel(null);
        adminPanel2.setBounds(85, 0, 995, 695);
// Category Graphics
        addCatLabel = new JLabel("Category Name:");
        addCatLabel.setBounds(225, 112, 200, 36);
        addCatLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        addCatField = new JTextField();
        addCatField.setBounds(450, 116, 250, 31);

        addCatButton = new JButton("Add Category");
        addCatButton.setBounds(400, 215, 120, 36);
        addCatButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        delCatLabel = new JLabel("Select Category:");
        delCatLabel.setBounds(225, 112, 200, 36);
        delCatLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        delCatButton = new JButton("Delete");
        delCatButton.setBounds(410, 215, 100, 36);
        delCatButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

// Product Graphics
        pNameLabel = new JLabel("Product Name:");
        pNameLabel.setBounds(145, 100, 200, 36);
        pNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pNameField = new JTextField();
        pNameField.setBounds(355, 104, 250, 31);
        pNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter the product name");

        pDescLabel = new JLabel("Product Description:");
        pDescLabel.setBounds(80, 180, 250, 36);
        pDescLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pDescField = new JTextField();
        pDescField.setBounds(355, 184, 250, 31);
        pDescField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter the product description");

        pCatLabel = new JLabel("Product Category:");
        pCatLabel.setBounds(105, 260, 250, 36);
        pCatLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pPriceLabel = new JLabel("Product Price:");
        pPriceLabel.setBounds(155, 340, 200, 36);
        pPriceLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pPriceField = new JTextField();
        pPriceField.setBounds(355, 344, 250, 31);
        pPriceField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter the product price");

        pQuantityLabel = new JLabel("Product Quantity:");
        pQuantityLabel.setBounds(110, 420, 250, 36);
        pQuantityLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pQuantityField = new JTextField();
        pQuantityField.setBounds(355, 424, 250, 31);
        pQuantityField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter the product quantity");

        pAddImageLabel = new JLabel("Product Image:");
        pAddImageLabel.setBounds(705, 55, 195, 36);
        pAddImageLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        pImageLabel = new JLabel();
        pImageLabel.setBounds(700, 104, 200, 200);
        pImageLabel.setBorder(BorderFactory.createLineBorder(Color.getColor(TOOL_TIP_TEXT_KEY), 2));

        addProdButton = new JButton("Add Product");
        addProdButton.setBounds(290, 520, 110, 36);
        addProdButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        addImageButton = new JButton("Add Image");
        addImageButton.setBounds(752, 325, 100, 36);
        addImageButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        selectPLabel = new JLabel("Select Product:");
        selectPLabel.setBounds(225, 192, 200, 36);
        selectPLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));

        updatePButton = new JButton("Update Product");
        updatePButton.setBounds(265, 520, 135, 36);
        updatePButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        delPButton = new JButton("Delete Product");
        delPButton.setBounds(420, 300, 125, 36);
        delPButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));

// Product Graphics
        salesLabel = new JLabel("Total Sales");
        salesLabel.setBounds(75, 50, 180, 36);
        salesLabel.setFont(new Font("Arial", Font.PLAIN, 28));

        reportButton = new JButton("Generate Report");
        reportButton.setBounds(775, 50, 125, 36);
        reportButton.setFont(new Font("Arial", Font.PLAIN, 14));

        add(adminPanel1);
        add(adminPanel2);

        menuButton.addActionListener(this);
        modeButton.addActionListener(this);
        logoutButton.addActionListener(this);

        addImageButton.addActionListener(this);
        addProdButton.addActionListener(this);
        updatePButton.addActionListener(this);
        delPButton.addActionListener(this);

        addCatButton.addActionListener(this);
        delCatButton.addActionListener(this);

        reportButton.addActionListener(this);

        setLayout(null);
        setBounds(0, 0, 1080, 720);
    }

    public static AdminPanel getInstance() {
        if (instance == null) {
            instance = new AdminPanel(BusinessLogic.getInstance());
        }
        return instance;
    }

    public void menuActions(int menu, int subMenu) {
        if (menu == 0 && subMenu == 1) {
            catComboBox = new JComboBox(bll.getCategories());
            catComboBox.setBounds(355, 264, 250, 31);

            pNameField.setText("");
            pDescField.setText("");
            pPriceField.setText("");
            pQuantityField.setText("");
            pImageLabel.setIcon(null);

            FlatAnimatedLafChange.showSnapshot();

            adminPanel2.removeAll();

            adminPanel2.add(pNameLabel);
            adminPanel2.add(pNameField);
            adminPanel2.add(pDescLabel);
            adminPanel2.add(pDescField);
            adminPanel2.add(pCatLabel);
            adminPanel2.add(catComboBox);
            adminPanel2.add(pPriceLabel);
            adminPanel2.add(pPriceField);
            adminPanel2.add(pQuantityLabel);
            adminPanel2.add(pQuantityField);

            adminPanel2.add(pAddImageLabel);
            adminPanel2.add(pImageLabel);

            adminPanel2.add(addImageButton);
            adminPanel2.add(addProdButton);

            adminPanel2.repaint();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }
        if (menu == 0 && subMenu == 2) {
            catComboBox = new JComboBox(bll.getCategories());
            catComboBox.setBounds(450, 116, 250, 31);

            FlatAnimatedLafChange.showSnapshot();

            adminPanel2.removeAll();

            adminPanel2.add(delCatLabel);
            adminPanel2.add(catComboBox);

            adminPanel2.repaint();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            catComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    catComboBox.setEnabled(false);
                    proComboBox = new JComboBox(bll.getProducts((String) catComboBox.getItemAt(catComboBox.getSelectedIndex())));
                    proComboBox.setBounds(450, 196, 250, 31);

                    FlatAnimatedLafChange.showSnapshot();

                    adminPanel2.add(selectPLabel);
                    adminPanel2.add(proComboBox);

                    adminPanel2.repaint();

                    FlatAnimatedLafChange.hideSnapshotWithAnimation();

                    proComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            catComboBox = new JComboBox(bll.getCategories());
                            catComboBox.setBounds(355, 264, 250, 31);

                            pNameField.setText((String) proComboBox.getItemAt(proComboBox.getSelectedIndex()));
                            pDescField.setText(bll.getPDesc((String) proComboBox.getItemAt(proComboBox.getSelectedIndex())));
                            pPriceField.setText(bll.getPPrice((String) proComboBox.getItemAt(proComboBox.getSelectedIndex())) + "");
                            pQuantityField.setText(bll.getPQuantity((String) proComboBox.getItemAt(proComboBox.getSelectedIndex())) + "");
                            pImageLabel.setIcon(bll.getPImage((String) proComboBox.getItemAt(proComboBox.getSelectedIndex())));

                            FlatAnimatedLafChange.showSnapshot();

                            adminPanel2.removeAll();

                            adminPanel2.add(pNameLabel);
                            adminPanel2.add(pNameField);
                            adminPanel2.add(pDescLabel);
                            adminPanel2.add(pDescField);
                            adminPanel2.add(pCatLabel);
                            adminPanel2.add(catComboBox);
                            adminPanel2.add(pPriceLabel);
                            adminPanel2.add(pPriceField);
                            adminPanel2.add(pQuantityLabel);
                            adminPanel2.add(pQuantityField);

                            adminPanel2.add(pAddImageLabel);
                            adminPanel2.add(pImageLabel);

                            adminPanel2.add(addImageButton);
                            adminPanel2.add(updatePButton);

                            adminPanel2.repaint();

                            FlatAnimatedLafChange.hideSnapshotWithAnimation();
                        }
                    });
                }
            });
        }
        if (menu == 0 && subMenu == 3) {
            catComboBox = new JComboBox(bll.getCategories());
            catComboBox.setBounds(450, 116, 250, 31);

            FlatAnimatedLafChange.showSnapshot();

            adminPanel2.removeAll();

            adminPanel2.add(delCatLabel);
            adminPanel2.add(catComboBox);

            adminPanel2.repaint();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();

            catComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    catComboBox.setEnabled(false);
                    proComboBox = new JComboBox(bll.getProducts((String) catComboBox.getItemAt(catComboBox.getSelectedIndex())));
                    proComboBox.setBounds(450, 196, 250, 31);

                    FlatAnimatedLafChange.showSnapshot();

                    adminPanel2.add(selectPLabel);
                    adminPanel2.add(proComboBox);

                    adminPanel2.repaint();

                    FlatAnimatedLafChange.hideSnapshotWithAnimation();

                    proComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            FlatAnimatedLafChange.showSnapshot();

                            adminPanel2.add(delPButton);

                            adminPanel2.repaint();

                            FlatAnimatedLafChange.hideSnapshotWithAnimation();
                        }
                    });
                }
            });
        }
        if (menu == 1 && subMenu == 1) {
            FlatAnimatedLafChange.showSnapshot();

            adminPanel2.removeAll();

            adminPanel2.add(addCatLabel);
            adminPanel2.add(addCatField);
            adminPanel2.add(addCatButton);

            adminPanel2.repaint();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }
        if (menu == 1 && subMenu == 2) {
            FlatAnimatedLafChange.showSnapshot();

            adminPanel2.removeAll();

            catComboBox = new JComboBox(bll.getCategories());
            catComboBox.setBounds(450, 116, 250, 31);

            adminPanel2.add(delCatLabel);
            adminPanel2.add(catComboBox);
            adminPanel2.add(delCatButton);

            adminPanel2.repaint();

            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }

        if (menu == 2) {

            salesTableModel = new DefaultTableModel();
            salesTableModel.addColumn("Product Name");
            salesTableModel.addColumn("Product Description");
            salesTableModel.addColumn("Product Price");

            salesTable = new JTable(salesTableModel);

            salesScrollPane = new JScrollPane(salesTable);
            salesScrollPane.setBounds(75, 100, 830, 300);

            salesTableModel = bll.getSales(salesTableModel);

            revenueLabel = new JLabel("Total Revenue: " + bll.getRevenue() + " PKR");
            revenueLabel.setBounds(75, 425, 415, 36);
            revenueLabel.setFont(new Font("Arial", Font.PLAIN, 26));

            adminPanel2.removeAll();

            adminPanel2.add(salesLabel);
            adminPanel2.add(salesScrollPane);
            adminPanel2.add(revenueLabel);
            adminPanel2.add(reportButton);

            adminPanel2.repaint();

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
                adminPanel1.setBackground(new Color(50, 50, 50));
                adminPanel1.repaint();
            } else {
                FlatAnimatedLafChange.showSnapshot();
                FlatMacLightLaf.setup();
                menuButton.setIcon(new FlatSVGIcon("pl/icons/menuLight.svg", 25, 25));
                menuButton.setBackground(new Color(0, 122, 255));
                modeButton.setIcon(new FlatSVGIcon("pl/icons/light.svg", 25, 25));
                modeButton.setBackground(new Color(0, 122, 255));
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                adminPanel1.setBackground(new Color(220, 235, 250));
                adminPanel1.repaint();
            }
        }
        if (e.getSource() == logoutButton) {
            FlatAnimatedLafChange.showSnapshot();
            PresentationLayer.getInstance().setContentPane(new Login(bll));
            PresentationLayer.getInstance().revalidate();
            PresentationLayer.getInstance().repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        }
        if (e.getSource() == addImageButton) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                File file = fileChooser.getSelectedFile();
                if (file != null) {
                    imagePath = file.getAbsolutePath();
                    BufferedImage bi = ImageIO.read(new File(imagePath));
                    Image image = bi.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    pImageLabel.setIcon(icon);
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == addProdButton) {
            if (bll.addProduct(pNameField.getText(), pDescField.getText(), (String) catComboBox.getItemAt(catComboBox.getSelectedIndex()), pPriceField.getText(), pQuantityField.getText(), imagePath)) {
                MessageAlerts.getInstance().showMessage("Successful!", "Product Added Successfully", MessageAlerts.MessageType.SUCCESS);
                adminPanel2.removeAll();
                adminPanel2.repaint();
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is an error in adding product", MessageAlerts.MessageType.ERROR);
            }
            imagePath = null;
        }
        if (e.getSource() == updatePButton) {
            if (bll.updateProduct((String) proComboBox.getItemAt(proComboBox.getSelectedIndex()), pNameField.getText(), pDescField.getText(), (String) catComboBox.getItemAt(catComboBox.getSelectedIndex()), pPriceField.getText(), pQuantityField.getText(), imagePath)) {
                MessageAlerts.getInstance().showMessage("Successful!", "Product Updated Successfully", MessageAlerts.MessageType.SUCCESS);
                adminPanel2.removeAll();
                adminPanel2.repaint();
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is an error in updating product", MessageAlerts.MessageType.ERROR);
            }
            imagePath = null;
        }
        if (e.getSource() == delPButton) {
            if (bll.deleteProduct((String) proComboBox.getItemAt(proComboBox.getSelectedIndex()))) {
                MessageAlerts.getInstance().showMessage("Successful!", "Product Deleted Successfully", MessageAlerts.MessageType.SUCCESS);
                adminPanel2.removeAll();
                adminPanel2.repaint();
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is an error in deleting product", MessageAlerts.MessageType.ERROR);
            }
        }
        if (e.getSource() == addCatButton) {
            if (bll.addCategory(addCatField.getText())) {
                MessageAlerts.getInstance().showMessage("Successful!", "Category Added Successfully", MessageAlerts.MessageType.SUCCESS);

                FlatAnimatedLafChange.showSnapshot();
                adminPanel2.removeAll();
                adminPanel2.repaint();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is an error in adding your category", MessageAlerts.MessageType.ERROR);
            }
        }
        if (e.getSource() == delCatButton) {
            if (bll.deleteCategory((String) catComboBox.getItemAt(catComboBox.getSelectedIndex()))) {
                MessageAlerts.getInstance().showMessage("Successful!", "Category Deleted Successfully", MessageAlerts.MessageType.SUCCESS);

                FlatAnimatedLafChange.showSnapshot();
                adminPanel2.removeAll();
                adminPanel2.repaint();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            } else {
                MessageAlerts.getInstance().showMessage("Error!", "There is an error in deleting your category", MessageAlerts.MessageType.ERROR);
            }
        }
        if (e.getSource() == reportButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Location");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                bll.generateReport(salesTable, fileChooser.getSelectedFile().getAbsolutePath() + ".pdf");
            }
        }
    }
}
