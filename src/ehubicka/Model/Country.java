package ehubicka.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Country {
    @FXML
    private int countryId;
    @FXML
    private String countryName;
    @FXML
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the string and not a hash code
        return (countryName);
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
