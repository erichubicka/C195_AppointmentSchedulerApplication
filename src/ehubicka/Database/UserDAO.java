package ehubicka.Database;

import ehubicka.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for accessing the users table in the MySql database.
 */
public class UserDAO {
    /**
     * Used for getting all the users in the database and returning them as a list.
     * @return the userList.
     */
    public static ObservableList<User> getAllUsers(){ //method used in the Mainscreen class
        ObservableList<User> userList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM users"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the user database query results rs
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User us = new User(userId, userName, userPassword); // create object instance of User us
                userList.add(us); //add object instance to the userList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList; //return he userList containing the object records
    }
}
