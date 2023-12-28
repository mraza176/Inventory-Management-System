package dal;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DataAccess {

    private static DataAccess instance;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    String JdbcUrl = "jdbc:mysql://localhost:3306/db_project", Username = "root", Password = "123", query;

    public DataAccess() {
        try {
            connection = DriverManager.getConnection(JdbcUrl, Username, Password);
            if (connection != null) {
                System.out.println("Connected to the Database!");
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public static DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

    public boolean registerUser(String firstName, String lastName, String gender, String username, String password) {
        try {
            query = "INSERT INTO usercredentials(First_Name, Last_Name, Gender, Username, Password) VALUES(?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public HashMap<String, String> getUser() {
        HashMap<String, String> user = new HashMap<>();
        try {
            query = "SELECT Username, Password FROM usercredentials";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                user.put(resultSet.getString("Username"), resultSet.getString("Password"));
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return user;
    }

    public String getUserName(String username) {
        try {
            query = "SELECT First_Name, Last_Name FROM usercredentials WHERE Username = '" + username + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                username = resultSet.getString("First_Name") + " " + resultSet.getString("Last_Name");
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return username;
    }

    public boolean addProduct(String pName, String pDesc, String pCat, int pPrice, int pQuantity, String imagePath) {
        try {
            query = "SELECT Cat_ID FROM categories WHERE Cat_Name = '" + pCat + "'";
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("Cat_ID");
            }
            query = "INSERT INTO product(Cat_ID, P_Name, P_Desc, P_Price, P_Quantity, P_Image) Values(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pName);
            preparedStatement.setString(3, pDesc);
            preparedStatement.setInt(4, pPrice);
            preparedStatement.setInt(5, pQuantity);
            InputStream is = new FileInputStream(new File(imagePath));
            preparedStatement.setBlob(6, is);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | FileNotFoundException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public String[] getProducts(String category) {
        String[] products = null;
        try {
            query = "SELECT Cat_ID FROM categories WHERE Cat_Name = '" + category + "'";
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("Cat_ID");
            }
            query = "SELECT P_Name FROM product WHERE Cat_ID = " + id;
            resultSet = statement.executeQuery(query);
            id = 0;
            while (resultSet.next()) {
                id++;
            }
            products = new String[id];
            resultSet = statement.executeQuery(query);
            id = 0;
            while (resultSet.next()) {
                products[id] = resultSet.getString("P_Name");
                id++;
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return products;
    }

    public String getPDesc(String pName) {
        String pDesc = null;
        try {
            query = "SELECT P_Desc FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                pDesc = resultSet.getString("P_Desc");
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return pDesc;
    }

    public int getPPrice(String pName) {
        int pPrice = 0;
        try {
            query = "SELECT P_Price FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                pPrice = resultSet.getInt("P_Price");
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return pPrice;
    }

    public int getPQuantity(String pName) {
        int pQuantity = 0;
        try {
            query = "SELECT P_Quantity FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                pQuantity = resultSet.getInt("P_Quantity");
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return pQuantity;
    }

    public ImageIcon getPImage(String pName) {
        ImageIcon pImage = null;
        try {
            Blob image = null;
            query = "SELECT P_Image FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                image = resultSet.getBlob("P_Image");
            }
            byte[] bs = image.getBytes(1, (int) image.length());
            pImage = new ImageIcon(bs);
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return pImage;
    }

    public boolean updateProduct(String oldPName, String pName, String pDesc, String pCat, int pPrice, int pQuantity, String imagePath) {
        try {
            query = "SELECT Cat_ID FROM categories WHERE Cat_Name = '" + pCat + "'";
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("Cat_ID");
            }
            if (imagePath != null) {
                query = "UPDATE product SET Cat_ID = ?, P_Name = ?, P_Desc = ?, P_Price = ?, P_Quantity = ?, P_Image = ? WHERE P_Name = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, pName);
                preparedStatement.setString(3, pDesc);
                preparedStatement.setInt(4, pPrice);
                preparedStatement.setInt(5, pQuantity);
                InputStream is = new FileInputStream(new File(imagePath));
                preparedStatement.setBlob(6, is);
                preparedStatement.setString(7, oldPName);
                preparedStatement.executeUpdate();
                return true;
            } else {
                query = "UPDATE product SET Cat_ID = ?, P_Name = ?, P_Desc = ?, P_Price = ?, P_Quantity = ? WHERE P_Name = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, pName);
                preparedStatement.setString(3, pDesc);
                preparedStatement.setInt(4, pPrice);
                preparedStatement.setInt(5, pQuantity);
                preparedStatement.setString(6, oldPName);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException | FileNotFoundException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(String pName) {
        try {
            query = "DELETE FROM product WHERE P_Name = '" + pName + "'";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> getInventory() {
        ArrayList<String> products = new ArrayList<>();
        try {
            query = "SELECT P_Name FROM product";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                products.add(resultSet.getString("P_Name"));
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return products;
    }

    public void updateAddPQuantity(String pName) {
        try {
            query = "SELECT P_Quantity FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            int quantity = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("P_Quantity");
            }
            query = "UPDATE product SET P_Quantity = ? WHERE P_Name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, --quantity);
            preparedStatement.setString(2, pName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public void updateRemovePQuantity(String pName) {
        try {
            query = "SELECT P_Quantity FROM product WHERE P_Name = '" + pName + "'";
            resultSet = statement.executeQuery(query);
            int quantity = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("P_Quantity");
            }
            query = "UPDATE product SET P_Quantity = ? WHERE P_Name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ++quantity);
            preparedStatement.setString(2, pName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public void addOrders(String username, String pName, String pDesc, int pPrice) {
        try {
            query = "SELECT User_ID FROM usercredentials WHERE Username = '" + username + "'";
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("User_ID");
            }
            query = "INSERT INTO orders(User_ID, P_Name, P_Desc, P_Price) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pName);
            preparedStatement.setString(3, pDesc);
            preparedStatement.setInt(4, pPrice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public DefaultTableModel getOrders(String username, DefaultTableModel orderTableModel) {
        try {
            query = "SELECT User_ID FROM usercredentials WHERE Username = '" + username + "'";
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("User_ID");
            }
            query = "SELECT P_Name, P_Desc, P_Price FROM orders WHERE User_ID = " + id;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String[] items = {resultSet.getString("P_Name"), resultSet.getString("P_Desc"), resultSet.getInt("P_Price") + ""};
                orderTableModel.addRow(items);
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return orderTableModel;
    }

    public DefaultTableModel getSales(DefaultTableModel salesTableModel) {
        try {
            query = "SELECT P_Name, P_Desc, P_Price FROM orders";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String[] items = {resultSet.getString("P_Name"), resultSet.getString("P_Desc"), resultSet.getInt("P_Price") + ""};
                salesTableModel.addRow(items);
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return salesTableModel;
    }

    public int getRevenue() {
        int revenue = 0;
        try {
            query = "SELECT P_Price FROM orders";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                revenue += resultSet.getInt("P_Price");
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return revenue;
    }

    public boolean addCategory(String catName) {
        try {
            query = "INSERT INTO categories(Cat_Name) VALUES(?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, catName);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public String[] getCategories() {
        String[] categories = null;
        try {
            query = "SELECT DISTINCT Cat_Name FROM categories";
            resultSet = statement.executeQuery(query);
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            categories = new String[count];
            resultSet = statement.executeQuery(query);
            count = 0;
            while (resultSet.next()) {
                categories[count] = resultSet.getString("Cat_Name");
                count++;
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return categories;
    }

    public boolean deleteCategory(String catName) {
        try {
            query = "DELETE FROM categories WHERE Cat_Name = '" + catName + "'";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            return false;
        }
    }
}
