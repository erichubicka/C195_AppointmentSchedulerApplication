package ehubicka.Database;

import ehubicka.Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for accessing the countries table in the MySql database.
 */
public class CountryDAO {
    /**
     * Used for getting all the countries in the database and returning them as a list.
     * @return the countryList.
     */
    public static ObservableList<Country> getAllCountries(){ //method used in the Mainscreen class
        ObservableList<Country> countryList = FXCollections.observableArrayList();//create list to return
        String sql = "SELECT * FROM countries"; //setup sql
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //create prepared statement
            ResultSet rs = ps.executeQuery(); // assign the query execution to the ResultSet rs
            while(rs.next()){ //iterate through each record and pull the following parameters from the countries database query results rs
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country cs = new Country(countryId, countryName); // create object instance of Country cs
                countryList.add(cs); //add object instance to the countryList to be returned later
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryList; //return he countryList containing the object records
    }
}
