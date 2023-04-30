package ehubicka.Database;


import ehubicka.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Used for accessing the customers table in the MySql database.
 */
public class CustomerDAO {
    /**
     * Used for getting all the customers in the database and returning them as a list.
     * @return the customerList.
     */
    public static ObservableList<Customer> getAllCustomers(){ //method used in the Mainscreen controller to populate the tableview
        ObservableList<Customer> customerList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM customers " //select all columns and records from customers table
                + "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " //link customers and first_level_divisions tables by Division_ID
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID"; //link first_level_divisions and countries tables by Country_ID
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while(rs.next()){ //iterate through each record and pull the following parameters from the customers database query results rs
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");
                String countryName = rs.getString("Country");
//                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
//                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
//                String createdBy = rs.getString("Created_By");
//                String lastUpdatedBy = rs.getString("Last_Updated_By");
//                int divisionId = rs.getInt("Division_ID");

                Customer cs = new Customer(customerId, customerName, address, postalCode, phone, division, countryName); // create object instance of Customer cs
                customerList.add(cs); //add object instance to the customerList to be returned later
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return customerList; //return he customerList containing the object records
    }

    /**
     * Get the corresponding division ID from a division name.
     * @param division is the division name to be passed in as a String.
     * @return the corresponding division ID.
     * @throws SQLException for errors related to interacting with the database.
     */
    public static int getDivisionID(String division) throws SQLException{
        int divisionID = 0; //assign 0 to the divisionID variable to be returned by passing in a division name
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?"; //sql query
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ps.setString(1, division); //set the division parameter to the PreparedStatement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through the database records fetching the divisionID's
                divisionID = rs.getInt("Division_ID"); //get the Division_ID from the mysql database
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return divisionID; //return the divisionID associated with the specific division name input parameter
    }

    /**
     * Insert a new customer record into the database.
     * @param customerName is the name of the customer.
     * @param address is the address of the customer.
     * @param postalCode is the postal code of the customer.
     * @param phone is the phone number of the customer.
     * @param division is the first-level-division of the customer.
     * @param country is the country of the customer.
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, String division, String country) { //method used in AddCustomer controller
        //            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (NULL, ?, ?, ?, ?, NOW(), 'User', NOW(), 'User', ?)";
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //inject the sql into the mysql database
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, (getDivisionID(division)));
            ps.executeUpdate();

        }catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    /**
     * Modify an existing record of a customer in the database.
     * @param customerId is the unique customer id for the customer (primary key).
     * @param customerName is the name of the customer.
     * @param address is the address of the customer.
     * @param postalCode is the postal code of the customer.
     * @param phone is the phone number of the customer.
     * @param division is the first-level-division of the customer.
     * @param country is the country of the customer.
     */
    public static void update(int customerId, String customerName, String address, String postalCode, String phone, String division, String country) {
        try{
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, (getDivisionID(division)));
            ps.setInt(6, customerId);
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    /**
     * Remove an existing record of a customer in the database.
     * @param customerId is the unique customer id for the customer (primary key).
     * @return how many records were removed. Essentially verifies that a customer record was deleted.
     * @throws SQLException for database errors.
     */
    public static int deleteCustomer(int customerId) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
