package ehubicka.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Division {
    @FXML
    private int division_ID;
    @FXML
    private String division;
    @FXML
    private int country_ID;
    @FXML
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    public Division(int division_ID, String division, int country_ID) {
        this.division_ID = division_ID;
        this.division = division;
        this.country_ID = country_ID;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the string and not a hash code
        return (division);
    }
    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }
    public int getDivision_ID() {
        return division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    public String getDivision() {return division;}

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountry_ID() {
        return country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }
}
