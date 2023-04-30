package ehubicka.Database;

import ehubicka.Model.Appointment;
import ehubicka.Model.ContactAppointment;
import ehubicka.Model.CustomerAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Used for accessing the appointments table in the MySql database.
 */
public class AppointmentDAO {
    /**
     * Used for getting all the appointments in the database and returning them as a list.
     * @return the appointmentList.
     */
    public static ObservableList<Appointment> getAllAppointments() { //method used in the Mainscreen controller to populate the tableview
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments " //select all columns and records from appointments table
                + "JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "JOIN users ON appointments.User_ID = users.User_ID "
                + "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");
                String userName = rs.getString("User_Name");

                Appointment ap = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName, customerName, userName); // create object instance of an appointment ap
                appointmentList.add(ap); //add object instance to the appointmentList to be returned later
            }
            } catch (SQLException e){
//            throw new RuntimeException(e);
                e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Get all the appointments for the current month. For example if the current month is April, get
     * appointments for April 1st to April 30th.
     * @return appointmentList
     */
    public static ObservableList<Appointment> getMonthAppointments() { //method used in the Mainscreen controller to populate the tableview
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments " //select all columns and records from appointments table
                + "JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "JOIN users ON appointments.User_ID = users.User_ID "
                + "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE MONTH(appointments.Start) = MONTH(current_date())";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");
                String userName = rs.getString("User_Name");

                Appointment ap = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName, customerName, userName); // create object instance of an appointment ap
                appointmentList.add(ap); //add object instance to the appointmentList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Get all the appointments for the upcoming week. This will display appointments for the next 7 days
     * including the current date.
     * @return appointmentList
     */
    public static ObservableList<Appointment> getWeekAppointments() { //method used in the Mainscreen controller to populate the tableview
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments " //select all columns and records from appointments table
                + "JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "JOIN users ON appointments.User_ID = users.User_ID "
                + "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE WEEK(Start) = WEEK(NOW())";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");
                String userName = rs.getString("User_Name");

                Appointment ap = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName, customerName, userName); // create object instance of an appointment ap
                appointmentList.add(ap); //add object instance to the appointmentList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Get all appointments starting within the next 15 minutes.
     * @return appointmentList
     */
    public static ObservableList<Appointment> get15MinuteAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments " //select all columns and records from appointments table
                + "JOIN customers ON appointments.Customer_ID = customers.Customer_ID "
                + "JOIN users ON appointments.User_ID = users.User_ID "
                + "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE appointments.Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");
                String userName = rs.getString("User_Name");

                Appointment ap = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName, customerName, userName); // create object instance of an appointment ap
                appointmentList.add(ap); //add object instance to the appointmentList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Method used in the reports controller for populating the appointment Types combo box (in report 1)
     * @return appointmentTypeList
     */
    public static ObservableList<String> getAllAppointmentTypes() {
        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT Type FROM appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                String type = rs.getString("Type");
                appointmentTypeList.add(type); //add object instance to the appointmentTypeList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentTypeList;
    }

    /**
     * Method used in the reports controller for getting the appointment counts per month for a given appointment
     * type. Used in the first report.
     * @param type is the type of appointment.
     * @param month is the specific month where appointments will be tallied.
     * @return the count for month.
     */
    public static int getMonthTypeCounts(String type, int month) { //method used in the reports controller
        int count = 0;
        //setup sql
        String sql = "SELECT COUNT(*) FROM appointments "
                + "WHERE Type = ? AND MONTH(Start) = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ps.setString(1, type);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Method used in the reports controller for getting all the contacts used for populating the contacts
     * combo box used in second report.
     * @return appointmentContactList
     */
    public static ObservableList<String> getAllAppointmentContacts() {
        ObservableList<String> appointmentContactList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments "
                + "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                String contactName = rs.getString("Contact_Name");
                appointmentContactList.add(contactName); //add object instance to the appointmentContactList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentContactList;
    }

    /**
     * Method used in the reports controller for getting all the appointments given a particular contact name.
     * Used in the 2nd report.
     * @param contactName is the name of the contact.
     * @return appointmentList
     */
    public static ObservableList<ContactAppointment> getAppointmentsByContactName(String contactName) {
        ObservableList<ContactAppointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE Contact_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                String contact_Name = rs.getString("Contact_Name");

                // Create a new Appointment object with the retrieved data
                ContactAppointment appointment = new ContactAppointment(appointmentId, title, description, type, startDateTime, endDateTime, customerId, contact_Name);
                appointmentList.add(appointment);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Method used in the reports controller for populating the appointment customers combo box used in report 3.
     * @return appointmentCustomerList
     */
    public static ObservableList<String> getAllAppointmentCustomers() {
        ObservableList<String> appointmentCustomerList = FXCollections.observableArrayList();//create list to return
        //setup sql
        String sql = "SELECT * FROM appointments "
                + "JOIN customers ON appointments.Customer_ID = customers.Customer_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs

            while (rs.next()) { //iterate through each record and pull the following parameters from the appointments database query results rs
                String customerName = rs.getString("Customer_Name");
                appointmentCustomerList.add(customerName); //add object instance to the appointmentContactList to be returned later
            }
        } catch (SQLException e){
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return appointmentCustomerList;
    }

    /**
     * Method used in the reports controller for getting all the appointments associated with a particular
     * customer name. Used in report 3.
     * @param customerName is the name of the customer.
     * @return customerList
     */
    public static ObservableList<CustomerAppointment> getAppointmentByCustomer(String customerName){
        ObservableList<CustomerAppointment> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                "WHERE Customer_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();

                CustomerAppointment appointment = new CustomerAppointment(appointmentId, startDateTime, endDateTime, customerName);
                customerList.add(appointment);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * Insert a new appointment record into the database.
     * @param title is the appointment title.
     * @param description is the appointment description.
     * @param location is the appointment location.
     * @param type is the appointment type.
     * @param startDateTime is the appointment starting date and timestamp.
     * @param endDateTime is the appointment ending date and timestamp.
     * @param customerId is the customer Id associated with the appointment.
     * @param userId is the user Id associated with the appointment.
     * @param contactId is the contact Id associated with the appointment.
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId){
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //inject the sql into the mysql database
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    /**
     * Modify an existing record of an appointment in the database.
     * @param appointmentId is the unique id for the appointment (primary key).
     * @param title is the appointment title.
     * @param description is the appointment description.
     * @param location is the appointment location.
     * @param type is the appointment type.
     * @param startDateTime is the appointment starting date and timestamp.
     * @param endDateTime is the appointment ending date and timestamp.
     * @param customerId is the customer Id associated with the appointment.
     * @param userId is the user Id associated with the appointment.
     * @param contactId is the contact Id associated with the appointment.
     */
    public static void update(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId){
        String sql =  "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_Id = ? WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3,location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * Remove an existing record of an appointment in the database.
     * @param appointmentId is the unique id for the appointment (primary key).
     * @return how many records were removed. Essentially verifies that an appointment record was deleted.
     * @throws SQLException for database errors.
     */
    public static int deleteAppointment(int appointmentId) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}