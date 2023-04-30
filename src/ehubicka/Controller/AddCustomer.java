package ehubicka.Controller;

import ehubicka.Database.CountryDAO;
import ehubicka.Database.CustomerDAO;
import ehubicka.Database.DivisionDAO;
import ehubicka.Model.Country;
import ehubicka.Model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * This class will add a customer record to the database. Users will be prompted to enter various information
 * not including a customer ID which will be generated automatically. Upon saving the customer, it will
 * be displayed in the mainscreen customers tableview.
 */
public class AddCustomer {
    @FXML
    private TextField customerId;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerPhone;
    @FXML
    private TextField customerPostal;
    @FXML
    private TextField customerAddress;
    @FXML
    private ComboBox customerCountry;
    @FXML
    private ComboBox customerDivision;

    /**
     * Used to initialize the controller with any code that needs to be implemented when this screen is accessed.
     * The combo boxes will be populated with choices to select.
     */
    public void initialize() {
        //populate combobox selections
        ObservableList<Country> countries = CountryDAO.getAllCountries();
        ObservableList<Division> divisions = DivisionDAO.getUSDivisions();
        customerCountry.setItems(countries);
        customerCountry.getSelectionModel().selectFirst();
        customerDivision.setItems(divisions);
        customerDivision.getSelectionModel().selectFirst();

//        System.out.println(customerCountry.getSelectionModel().getSelectedItem().toString());
    }

    /**
     * Discard the customer to be added and takes the user back to the mainscreen.
     * @param actionEvent when the cancel button is pressed.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 1040, 630); //create new scene
        stage.setTitle("Main Screen"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * Input from the user (minus a customer ID which will be automatically generated) will be added as a record
     * in the database. The user will then be taken to the mainscreen.
     * @param actionEvent when the save button is pressed.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        try{
            String name = customerName.getText();
            String address = customerAddress.getText();
            String postalCode = customerPostal.getText();
            String phone = customerPhone.getText();
            String division = customerDivision.getSelectionModel().getSelectedItem().toString();
            String country = customerCountry.getSelectionModel().getSelectedItem().toString();

            if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Form Error");
                alert.setHeaderText("Form Error");
                alert.setContentText("No fields can be left blank.");
                alert.showAndWait();
            } else {
                CustomerDAO.insertCustomer(name, address, postalCode, phone, division, country);
//        System.out.println(name + " " + address + " " + postalCode + " " + phone + " " + division + " " + country);

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
                Scene scene = new Scene(root, 1040, 630); //create new scene
                stage.setTitle("Main Screen"); // set title of new scene
                stage.setScene(scene); //set new scene to the stage
                stage.show(); //show new scene on the stage
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error");
            alert.setHeaderText("Form Error");
            alert.setContentText("No fields can be left blank.");
            alert.showAndWait();
        }
    }

    /**
     * Filter divisions combo box based on which country is selected. For example, only US states will be
     * available to be selected when the country combo box is selected as US.
     * @param actionEvent when the country combo box detects a selection change.
     * @throws IOException most likely if the database cannot be accessed.
     */
    public void countryComboBoxOnAction(ActionEvent actionEvent) throws IOException { //detect when the country combobox is changed
            String country = customerCountry.getSelectionModel().getSelectedItem().toString(); //assign the currently selected country to a string variable

        if (country.equals("U.S")) { //if the country combobox is selected as U.S
            ObservableList<Division> divisionsUS = DivisionDAO.getUSDivisions(); //get the U.S divisions list
            customerDivision.setItems(divisionsUS); //assign U.S divisions list to the divisions combobox
            customerDivision.getSelectionModel().selectFirst(); //auto-select the first option from the divisions combbox
        } else if (country.equals("Canada")) {
            ObservableList<Division> divisionsCanada = DivisionDAO.getCanadaDivisions();
            customerDivision.setItems(divisionsCanada);
            customerDivision.getSelectionModel().selectFirst();
        } else {
            ObservableList<Division> divisionsUK = DivisionDAO.getUKDivisions();
            customerDivision.setItems(divisionsUK);
            customerDivision.getSelectionModel().selectFirst();
        }
    }
}
