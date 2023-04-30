package ehubicka.Database;

import ehubicka.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for accessing the contacts table in the MySql database.
 */
public class ContactDAO {
    /**
     * Used for getting all the contacts in the database and returning them as a list.
     * @return the contactList.
     */
    public static ObservableList<Contact> getAllContacts(){ //method used in the Mainscreen class
        ObservableList<Contact> contactList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM contacts"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the contacts database query results rs
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact ct = new Contact(contactId, contactName, email); // create object instance of Contact ct
                contactList.add(ct); //add object instance to the contactList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactList; //return he contactList containing the object records
    }
}
