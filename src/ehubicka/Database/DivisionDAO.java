package ehubicka.Database;

import ehubicka.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for accessing the divisions table in the MySql database.
 */
public class DivisionDAO {
    /**
     * Used for getting all the divisions in the database and returning them as a list.
     * @return the divisionList.
     */
    public static ObservableList<Division> getAllDivisions(){ //method used in the Mainscreen class
        ObservableList<Division> divisionList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM first_level_divisions"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the division database query results rs
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division ds = new Division(divisionId, divisionName, countryId); // create object instance of Division ds
                divisionList.add(ds); //add object instance to the divisionList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList; //return he divisionList containing the object records
    }

    /**
     * Used for getting all the US divisions in the database and returning them as a list.
     * @return the divisionList.
     */
    public static ObservableList<Division> getUSDivisions(){ //method used in the Mainscreen class
        ObservableList<Division> divisionList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the division database query results rs
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division ds = new Division(divisionId, divisionName, countryId); // create object instance of Division ds
                divisionList.add(ds); //add object instance to the divisionList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList; //return he divisionList containing the object records
    }

    /**
     * Used for getting all the Canada divisions in the database and returning them as a list.
     * @return the divisionList.
     */
    public static ObservableList<Division> getCanadaDivisions(){ //method used in the Mainscreen class
        ObservableList<Division> divisionList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the division database query results rs
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division ds = new Division(divisionId, divisionName, countryId); // create object instance of Division ds
                divisionList.add(ds); //add object instance to the divisionList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList; //return he divisionList containing the object records
    }

    /**
     * Used for getting all the UK divisions in the database and returning them as a list.
     * @return the divisionList.
     */
    public static ObservableList<Division> getUKDivisions(){ //method used in the Mainscreen class
        ObservableList<Division> divisionList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the division database query results rs
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division ds = new Division(divisionId, divisionName, countryId); // create object instance of Division ds
                divisionList.add(ds); //add object instance to the divisionList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList; //return he divisionList containing the object records
    }
}
