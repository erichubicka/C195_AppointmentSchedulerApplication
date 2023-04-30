package ehubicka.Controller;

import ehubicka.Database.AppointmentDAO;
import ehubicka.Database.CustomerDAO;
import ehubicka.Model.Appointment;
import ehubicka.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class will service as the homepage of the application once a user successfully log's in. It displays the
 * appointments table and the customers table. Customers and appointments can be added, updated or deleted.
 * Customers can be associated with multiple appointments.
 */
public class MainScreen implements Initializable {
    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn customerIdCol;
    @FXML
    private TableColumn customerNameCol;
    @FXML
    private TableColumn customerPhoneCol;
    @FXML
    private TableColumn customerPostalCol;
    @FXML
    private TableColumn customerAddressCol;
    @FXML
    private TableColumn customerCountryCol;
    @FXML
    private TableColumn customerDivisionCol;
    @FXML
    private TableColumn appointmentIdCol;
    @FXML
    private TableColumn appointmentTitleCol;
    @FXML
    private TableColumn appointmentDescriptionCol;
    @FXML
    private TableColumn appointmentLocationCol;
    @FXML
    private TableColumn appointmentContactCol;
    @FXML
    private TableColumn appointmentTypeCol;
    @FXML
    private TableColumn appointmentStartCol;
    @FXML
    private TableColumn appointmentEndCol;
    @FXML
    private TableColumn appointmentCustomerIdCol;
    @FXML
    private TableColumn appointmentUserIdCol;
    @FXML
    private TableView appointmentsTable;
    @FXML
    private ToggleGroup appointmentView;
    @FXML
    private RadioButton allRB;
    @FXML
    private RadioButton monthRB;
    @FXML
    private RadioButton weekRB;
    @FXML
    private Label localDateTime;
    @FXML
    private Label utcDateTime;
    @FXML
    private Label estDateTime;

    ObservableList<Appointment> appointmentMainScreenList = FXCollections.observableArrayList();

    /**
     * The appointments and customers tableviews are populated with existing records from the database.
     * The current local, EST, and UTC times are also displayed on the screen for informational purposes.
     * @param url passes the resources associated with the FXML file
     * @param resourceBundle manages location specific resources (strings, various data-types, etc)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerMainScreenList = CustomerDAO.getAllCustomers(); //method from the CustomerDAO class used to populate a list
        customersTable.setItems(customerMainScreenList);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        appointmentMainScreenList = AppointmentDAO.getAllAppointments();
        appointmentsTable.setItems(appointmentMainScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //get the users local time/date and assign them to a label
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        localDateTime.setText(formattedDateTime);
        //get the UTC time/date and assign them to a label
        LocalDateTime nowUTC = LocalDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTimeUTC = nowUTC.format(formatterUTC);
        utcDateTime.setText(formattedDateTimeUTC);
        //get the EST time/date and assign them to a label
        LocalDateTime nowEST = LocalDateTime.now(ZoneId.of("America/New_York"));
        DateTimeFormatter formatterEST = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTimeEST = nowEST.format(formatterEST);
        estDateTime.setText(formattedDateTimeEST);
    }

    //        -----------------------Various Functions-----------------------

    /**
     * Logout the user and take them back to the login screen.
     * @param actionEvent when the logout button is selected.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onLogoutClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 370, 225); //create new scene
        stage.setTitle(""); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * Take the user to the reports screen.
     * @param actionEvent when the reports button is selected.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onReportClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Report.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 1000, 450); //create new scene
        stage.setTitle("Reports"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    //

    /**
     * For updating the times located in the bottom right corner of the mainscreen (as they don't update automatically).
     * @param actionEvent when the update times button is selected.
     */
    public void onUpdateTimesClick(ActionEvent actionEvent) {
        //get the users local time/date and assign them to a label
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        localDateTime.setText(formattedDateTime);
        //get the UTC time/date and assign them to a label
        LocalDateTime nowUTC = LocalDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTimeUTC = nowUTC.format(formatterUTC);
        utcDateTime.setText(formattedDateTimeUTC);
        //get the EST time/date and assign them to a label
        LocalDateTime nowEST = LocalDateTime.now(ZoneId.of("America/New_York"));
        DateTimeFormatter formatterEST = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTimeEST = nowEST.format(formatterEST);
        estDateTime.setText(formattedDateTimeEST);
    }

    //        -----------------------Appointments-----------------------

    /**
     * The appointments table will be filtered to show only appointments scheduled for the current month.
     * @param actionEvent when the current month radio button is selected.
     */
    public void currentMonthRb(ActionEvent actionEvent) {
        appointmentMainScreenList.clear();
        appointmentMainScreenList = AppointmentDAO.getMonthAppointments();
        appointmentsTable.setItems(appointmentMainScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * The appointments table will be filtered to show only appointments scheduled for the next week.
     * @param actionEvent when the current week radio button is selected.
     */
    public void currentWeekRb(ActionEvent actionEvent) {
        appointmentMainScreenList.clear();
        appointmentMainScreenList = AppointmentDAO.getWeekAppointments();
        appointmentsTable.setItems(appointmentMainScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * All appointments regardless of dates and times will be shown in the appointments table.
     * @param actionEvent when the all appointments radio button is selected.
     */
    public void allAppointmentsRb(ActionEvent actionEvent) {
        appointmentMainScreenList.clear();
        appointmentMainScreenList = AppointmentDAO.getAllAppointments();
        appointmentsTable.setItems(appointmentMainScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Users are taken to the add appointments screen.
     * @param actionEvent when the add appointment button is selected.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onAddAppointmentClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 350, 450); //create new scene
        stage.setTitle("Add Appointment"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * Users are taken to the update appointments screen. An error will display if a record in the appointment tableview
     * is not selected before pressing the update button.
     * @param actionEvent when the update appointment button is selected.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onUpdateAppointmentClick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointment modifyAppointment = loader.getController();
            modifyAppointment.sendAppointment((Appointment) appointmentsTable.getSelectionModel().getSelectedItem()); //get the selected appointment in the appointment table

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e){ //alert if no appointment is selected upon pressing the update button
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("Update Error");
            alert.setContentText("Please Select an Appointment Before Updating.");
            alert.showAndWait();
        }
    }

    /**
     * The main screen is updated after an appointment is selected to be removed. An error will display if no record
     * was selected to be removed before pressing the delete button.
     * @param actionEvent when the delete button is selected.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     * @throws SQLException if there is an issue with database interaction.
     */
    public void onDeleteAppointmentClick(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem(); //get the selected appointment in the appointment table
        if (selectedAppointment != null) {
            AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentId()); //call the deleteAppointment method from the AppointmentDAO class passing in the selected appointment as a parameter

            //refresh the main page after a appointment is deleted
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
            Scene scene = new Scene(root, 1040, 630); //create new scene
            stage.setTitle("Main Screen"); // set title of new scene
            stage.setScene(scene); //set new scene to the stage
            stage.show(); //show new scene on the stage

            Alert alert = new Alert(Alert.AlertType.INFORMATION); //custom message that the customer was deleted
            alert.setTitle("Appointment Deleted");
            alert.setHeaderText("Appointment Deleted");
            alert.setContentText("Appointment ID:" + selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getAppointmentType() + " has been successfully deleted.");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR); //alert if no appointment was selected before pressing the delete button
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please Select an Appointment Before Deleting.");
            alert.showAndWait();
        }
    }

    //        -----------------------Customers-----------------------

    /**
     * Users are taken to the add customers screen.
     * @param actionEvent when the add button is selected for the customer table.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onAddCustomerClick(ActionEvent actionEvent) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 350, 330); //create new scene
        stage.setTitle("Add Customer"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * Users are taken to the update customers screen. An error will display if a record in the customer tableview
     * is not selected before pressing the update button.
     * @param actionEvent when the update button is selected for the customer table.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onUpdateCustomerClick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCustomer.fxml"));
            loader.load();

            ModifyCustomer modifyCustomer = loader.getController();
            modifyCustomer.sendCustomer((Customer) customersTable.getSelectionModel().getSelectedItem()); //get the selected customer in the customer table

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e){ //alert if no customer is selected upon pressing the update button
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("Update Error");
            alert.setContentText("Please Select a Customer Before Updating.");
            alert.showAndWait();
        }
    }

    /**
     * The main screen is updated after a customer is selected to be removed. An error will display if no record
     * was selected to be removed before pressing the delete button.
     * @param actionEvent when the delete button is selected for the customer table.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     * @throws SQLException if there is an issue with database interaction.
     */
    public void onDeleteCustomerClick(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem(); //get the selected customer in the customer table
        if (selectedCustomer != null){
            try{
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerId()); //call the deleteCustomer method from the CustomerDAO class passing in the selected customer as a parameter
                //refresh the main page after a customer is deleted
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
                Scene scene = new Scene(root, 1040, 630); //create new scene
                stage.setTitle("Main Screen"); // set title of new scene
                stage.setScene(scene); //set new scene to the stage
                stage.show(); //show new scene on the stage

                Alert alert = new Alert(Alert.AlertType.INFORMATION); //custom message that the customer was deleted
                alert.setTitle("Customer Deleted");
                alert.setHeaderText("Customer Deleted");
                alert.setContentText(selectedCustomer.getCustomerName() + " has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLIntegrityConstraintViolationException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR); //alert if no customer was selected before pressing the delete button
                alert.setTitle("Delete Error");
                alert.setHeaderText("Delete Error");
                alert.setContentText("Appointments associated with customer must be deleted first.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR); //alert if no customer was selected before pressing the delete button
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please Select a Customer Before Deleting.");
            alert.showAndWait();
        }
    }
}
