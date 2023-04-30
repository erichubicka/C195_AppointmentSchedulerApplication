package ehubicka.Controller;

import ehubicka.Database.AppointmentDAO;
import ehubicka.Model.ContactAppointment;
import ehubicka.Model.CustomerAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration; //for finding the difference between the appointment start and end times in report 3
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * This class will display three separate reports. These reports do not print or export anything, but rather
 * serve as supplemental information.
 */
public class Report {
    //        -----------------------Report 1-----------------------
    @FXML
    private ComboBox<String> customerCount;
    @FXML
    private TextArea appTypeMonth;

    //        -----------------------Report 2-----------------------
    @FXML
    private TableView contactsTable;
    @FXML
    private TableColumn appointmentIdCol;
    @FXML
    private TableColumn appointmentTitleCol;
    @FXML
    private TableColumn appointmentDescriptionCol;
    @FXML
    private TableColumn appointmentTypeCol;
    @FXML
    private TableColumn appointmentStartCol;
    @FXML
    private TableColumn appointmentEndCol;
    @FXML
    private TableColumn appointmentCustomerIdCol;
    @FXML
    private ComboBox contactSelector;

    //        -----------------------Report 3-----------------------
    @FXML
    private ComboBox customerSelector;
    @FXML
    private TextArea customerAppLengths;

    //        -----------------------Report 2-----------------------
    ObservableList<ContactAppointment> appointmentContactScreenList = FXCollections.observableArrayList();

    /**
     * Used to initialize the controller with any code that needs to be implemented when this screen is accessed.
     * The first report will display the amount of appointments scheduled based on appointment type and month.
     * The second report displays associated appointments with each contact.
     * The third report will show how long each customer's associated appointments will take.
     */
    public void initialize(){
        //        -----------------------Report 1-----------------------
        ObservableList<String> appointments = AppointmentDAO.getAllAppointmentTypes();
        // use a Set to store the unique elements (otherwise duplicates will populate in the combobox)
        Set<String> uniqueAppointments = new HashSet<>(appointments);
        appointments.clear(); // clear the original list
        appointments.addAll(uniqueAppointments); // add the unique elements
        customerCount.setItems(appointments);
        customerCount.getSelectionModel().selectFirst();

        ObservableList<Integer> monthAppointmentCounts = FXCollections.observableArrayList(); // integer list to hold the counts for each month
        ObservableList<Integer> months = FXCollections.observableArrayList( // integer list which will hold the months (by their numbers 1 to 12)
                IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));

        for (int i = 0; i < 12; i++) {
            monthAppointmentCounts.add(AppointmentDAO.getMonthTypeCounts(customerCount.getValue(), months.get(i)));
        }
//        System.out.println(monthAppointmentCounts);

        ObservableList<String> monthNames = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");

        //write the months and counts to the text area
        String msg = "";
        for (int i = 0; i < 11; i++) {
            msg = msg + monthNames.get(i) + " = " + monthAppointmentCounts.get(i) + "\n";
        }
        msg = msg + monthNames.get(11) + " = " + monthAppointmentCounts.get(11);
        appTypeMonth.setText(msg);

        //        -----------------------Report 2-----------------------
        ObservableList<String> contacts = AppointmentDAO.getAllAppointmentContacts();
        // use a Set to store the unique elements (otherwise duplicates will populate in the combobox)
        Set<String> uniqueContacts = new HashSet<>(contacts);
        contacts.clear(); // clear the original list
        contacts.addAll(uniqueContacts); // add the unique elements
        contactSelector.setItems(contacts);
        contactSelector.getSelectionModel().selectFirst();

        // Set the initial appointment list to the first contact in the selector
        appointmentContactScreenList = AppointmentDAO.getAppointmentsByContactName(contactSelector.getValue().toString());
        contactsTable.setItems(appointmentContactScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //        -----------------------Report 3-----------------------
        ObservableList<String> customers = AppointmentDAO.getAllAppointmentCustomers();
        // use a Set to store the unique elements (otherwise duplicates will populate in the combobox)
        Set<String> uniqueCustomers = new HashSet<>(customers);
        customers.clear(); // clear the original list
        customers.addAll(uniqueCustomers); // add the unique elements
        customerSelector.setItems(customers);
        customerSelector.getSelectionModel().selectFirst();

        String selectedCustomer = customerSelector.getValue().toString();

        ObservableList<CustomerAppointment> customerAppointments = AppointmentDAO.getAppointmentByCustomer(selectedCustomer);
        String msgTxt = "";

        for (CustomerAppointment appointment : customerAppointments) {
            LocalDateTime startDateTime = appointment.getAppointmentStartDateTime();
            LocalDateTime endDateTime = appointment.getAppointmentEndDateTime();
            Duration duration = Duration.between(startDateTime, endDateTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            msgTxt = msgTxt + "The Appointment ID: " + appointment.getAppointmentId() + " will take " + hours + " hours and " + minutes + " minutes.\n";
        }
        customerAppLengths.setText(msgTxt);
    }

    /**
     * Users are taken back to the mainscreen.
     * @param actionEvent when the back button is clicked.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onBackClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 1040, 630); //create new scene
        stage.setTitle("Main Screen"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }

    /**
     * A LAMBDA EXPRESSION is used to map customer appointments counts and the numerical value of their respective
     * start date months. The lambda simplifies the code and removes the need for an explicit for loop. It also
     * removes the need to create a months observablelist to hold month values 1 to 12 while they are iterated through.
     * The function without the lambda is left commented out to show the difference in code .
     * The selected contact will have its number of appointments in each month displayed in a text area.
     * This is associated with the first report.
     * @param actionEvent when the appointment type combo box is changed.
     */
    public void appointmentCountOnAction(ActionEvent actionEvent) { //report 1
//        ObservableList<Integer> monthAppointmentCounts = FXCollections.observableArrayList(); // integer list to hold the counts for each month
//        ObservableList<Integer> months = FXCollections.observableArrayList( // integer list which will hold the months (by their numbers 1 to 12)
//                IntStream.rangeClosed(1, 12).boxed().toArray(Integer[]::new));
//        for (int i = 0; i < 12; i++) {
//            monthAppointmentCounts.add(AppointmentDAO.getMonthTypeCounts(customerCount.getValue(), months.get(i)));
//        }


        ObservableList<Integer> monthAppointmentCounts = FXCollections.observableArrayList( //create an integer arraylist to hold months by their numbers 1 to 12
                // lambda
        IntStream.rangeClosed(1, 12).map(month -> //uses an IntStream range 1 to 12 and maps them to...
                AppointmentDAO.getMonthTypeCounts(customerCount.getValue(), month)).boxed().toArray(Integer[]::new)); // IntStream is mapped to the corresponding customer appointment counts


        ObservableList<String> monthNames = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        //write the months and counts to the text area
        String msg = "";
        for (int i = 0; i < 11; i++) {
            msg = msg + monthNames.get(i) + " = " + monthAppointmentCounts.get(i) + "\n";
        }
        msg = msg + monthNames.get(11) + " = " + monthAppointmentCounts.get(11);
        appTypeMonth.setText(msg);
    }

    /**
     * Populates the appointment table used in the second report. Appointments are filtered based on which contact
     * is selected from the combo box.
     * @param actionEvent when the contact combo box is changed.
     */
    public void contactSelectOnAction(ActionEvent actionEvent) { //report 2
        appointmentContactScreenList = AppointmentDAO.getAppointmentsByContactName(contactSelector.getValue().toString());
        contactsTable.setItems(appointmentContactScreenList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Populates the associated text area with appointment ID's and their respective appointment lengths.
     * The results are filtered based on which customer is selected from the combo box. This is associated
     * with the third report.
     * @param actionEvent when the customer combo box is changed.
     */
    public void customerSelectOnAction(ActionEvent actionEvent) { //report 3
        String selectedCustomer = customerSelector.getValue().toString();

        ObservableList<CustomerAppointment> customerAppointments = AppointmentDAO.getAppointmentByCustomer(selectedCustomer);
        String msgTxt = "";

        for (CustomerAppointment appointment : customerAppointments) {
            LocalDateTime startDateTime = appointment.getAppointmentStartDateTime();
            LocalDateTime endDateTime = appointment.getAppointmentEndDateTime();
            Duration duration = Duration.between(startDateTime, endDateTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            msgTxt = msgTxt + "The Appointment with ID: " + appointment.getAppointmentId() + " will take " + hours + " hours and " + minutes + " minutes.\n";
        }
        customerAppLengths.setText(msgTxt);
    }
}
