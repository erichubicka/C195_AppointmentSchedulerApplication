package ehubicka.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Customer {
    @FXML
    private int customerId;
    @FXML
    private String customerName;
    @FXML
    private String address;
    @FXML
    private String postalCode;
    @FXML
    private String phone;
    @FXML
    private String division;
    @FXML
    private String countryName;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String division, String countryName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.countryName = countryName;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the custom string and not a hash code
        return "[" + customerId + "] " + customerName;
    }
    public static ObservableList<Customer> getAllCustomers() { return allCustomers; }

    //getters needed to populate mainscreen tableviews
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getDivision() {
        return division;
    }

    public String getCountryName() {
        return countryName;
    }

}
