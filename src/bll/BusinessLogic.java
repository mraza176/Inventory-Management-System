package bll;

import dal.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.table.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import raven.toast.*;

public class BusinessLogic {

    private static BusinessLogic instance;

    private DataAccess dal;

    public BusinessLogic(DataAccess dal) {

        this.dal = dal;
    }

    public static BusinessLogic getInstance() {
        if (instance == null) {
            instance = new BusinessLogic(DataAccess.getInstance());
        }
        return instance;
    }

    public boolean registerUser(String firstName, String lastName, String gender, String username, String password) {
        return dal.registerUser(firstName, lastName, gender, username, password);
    }

    public HashMap<String, String> getUser() {
        return dal.getUser();
    }

    public String getUserName(String username) {
        return dal.getUserName(username);
    }

    public boolean addProduct(String pName, String pDesc, String pCat, String pPrice, String pQuantity, String imagePath) {
        return dal.addProduct(pName, pDesc, pCat, Integer.parseInt(pPrice), Integer.parseInt(pQuantity), imagePath);
    }

    public String[] getProducts(String category) {
        return dal.getProducts(category);
    }

    public String getPDesc(String pName) {
        return dal.getPDesc(pName);
    }

    public int getPPrice(String pName) {
        return dal.getPPrice(pName);
    }

    public int getPQuantity(String pName) {
        return dal.getPQuantity(pName);
    }

    public ImageIcon getPImage(String pName) {
        java.awt.Image image = dal.getPImage(pName).getImage();
        BufferedImage bi = new BufferedImage(200, 200, BufferedImage.SCALE_SMOOTH);
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(image, 0, 0, 200, 200, null);
        graphics2D.dispose();
        return new ImageIcon(bi);
    }

    public boolean updateProduct(String oldPName, String pName, String pDesc, String pCat, String pPrice, String pQuantity, String imagePath) {
        return dal.updateProduct(oldPName, pName, pDesc, pCat, Integer.parseInt(pPrice), Integer.parseInt(pQuantity), imagePath);
    }

    public boolean deleteProduct(String pName) {
        return dal.deleteProduct(pName);
    }

    public ArrayList<String> getInventory() {
        return dal.getInventory();
    }

    public void updateAddPQuantity(String pName) {
        dal.updateAddPQuantity(pName);
    }

    public void updateRemovePQuantity(String pName) {
        dal.updateRemovePQuantity(pName);
    }

    public void addOrders(String username, String pName, String pDesc, String pPrice) {
        dal.addOrders(username, pName, pDesc, Integer.parseInt(pPrice));
    }

    public DefaultTableModel getOrders(String username, DefaultTableModel orderTableModel) {
        return dal.getOrders(username, orderTableModel);
    }

    public DefaultTableModel getSales(DefaultTableModel salesTableModel) {
        return dal.getSales(salesTableModel);
    }

    public int getRevenue() {
        return dal.getRevenue();
    }

    public void generateReport(JTable salesTable, String filePath) {
        System.out.println(filePath);
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("                       Total Sales Report\n\n", new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
            PdfPTable pdfPTable = new PdfPTable(salesTable.getColumnCount());
            for (int i = 0; i < salesTable.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(salesTable.getColumnName(i), new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(new BaseColor(0, 122, 255));
                cell.setFixedHeight(22);
                pdfPTable.addCell(cell);
            }
            for (int i = 0; i < salesTable.getRowCount(); i++) {
                for (int j = 0; j < salesTable.getColumnCount(); j++) {
                    pdfPTable.addCell(salesTable.getValueAt(i, j).toString());
                }
            }
            document.add(pdfPTable);
            document.add(new Paragraph("\n\n             Total Revenue: " + getRevenue() + " PKR", new Font(Font.FontFamily.HELVETICA, 14)));
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Report Generated Successfully");
            Desktop.getDesktop().open(new File(filePath));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public boolean addCategory(String catName) {
        return dal.addCategory(catName);
    }

    public String[] getCategories() {
        return dal.getCategories();
    }

    public boolean deleteCategory(String catName) {
        return dal.deleteCategory(catName);
    }
}
