package ehubicka.Controller;

import ehubicka.Database.AppointmentDAO;
import ehubicka.Database.ContactDAO;
import ehubicka.Database.CustomerDAO;
import ehubicka.Database.UserDAO;
import ehubicka.Model.Appointment;
import ehubicka.Model.Contact;
import ehubicka.Model.Customer;
import ehubicka.Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This class will add an appointment record to the database. Users will be prompted to enter various information
 * not including an appointment ID which will be generated automatically. Upon saving the appointment, it will
 * be displayed in the mainscreen appointments tableview.
 */
public class AddAppointment {
    @FXML
    private TextField appointmentId;
    @FXML
    private TextField appointmentTitle;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentType;
    @FXML
    private ComboBox<LocalTime> startTime;
    @FXML
    private ComboBox<LocalTime> endTime;
    @FXML
    private ComboBox<Contact> appointmentContact;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<Customer> customerId;
    @FXML
    private ComboBox<User> userId;

    /**
     * Used to initialize the controller with any code that needs to be implemented when this screen is accessed.
     * The combo boxes will be populated with choices to select.
     */
    public void initialize(){
        //populate combobox selections
        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
        customerId.setItems(customers);
        customerId.getSelectionModel().selectFirst();
        ObservableList<User> users = UserDAO.getAllUsers();
        userId.setItems(users);
        userId.getSelectionModel().selectFirst();
        ObservableList<Contact> contacts = ContactDAO.getAllContacts();
        appointmentContact.setItems(contacts);
        appointmentContact.getSelectionModel().selectFirst();

        //populate time combobox selections
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 40);
        while(start.isBefore(end.plusSeconds(1))){
            startTime.getItems().add(start);
            start = start.plusMinutes(10);
        }
        end = LocalTime.of(0, 0);
        while(end.isBefore(LocalTime.of(23, 50))){
            endTime.getItems().add(end);
            end = end.plusMinutes(10);
        }
        startTime.getSelectionModel().select(LocalTime.of(8, 0));
        endTime.getSelectionModel().select(LocalTime.of(8, 30));
    }

    /**
     * Users will be prompted to add various fields of information when creating an appointment. Validation will
     * include checks to ensure that appointment times fall within business hours and that multiple appointments
     * from the same customer do not overlap. Once the users saves the information as a new record, it will be
     * saved in the MySql database and the user will be taken to the mainscreen.
     * @param actionEvent when the save button is pressed.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file.
     */
    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        try{
            String title = appointmentTitle.getText();
            String description = appointmentDescription.getText();
            String location = appointmentLocation.getText();
            String type = appointmentType.getText();

            LocalTime start_Time = startTime.getValue();
            LocalDate start_Date = startDate.getValue();

            LocalTime end_Time = endTime.getValue();
            LocalDate end_Date = endDate.getValue();

            LocalDateTime endDateTime = LocalDateTime.of(end_Date, end_Time);
            LocalDateTime startDateTime = LocalDateTime.of(start_Date, start_Time);

            Customer customer_Id = customerId.getValue();
            User user_Id = userId.getValue();
            Contact contact_Id = appointmentContact.getValue();

            //the following code block is used for checking that times are within business hours
            //assign business hours to LocalTime variables
            LocalTime businessStart = LocalTime.parse("08:00");
            LocalTime businessEnd =  LocalTime.parse("22:00");
            // get the current date and time in the local time zone
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            // get the current date and time in the Eastern Time zone
            LocalDateTime nowEST = LocalDateTime.now(ZoneId.of("America/New_York"));
            DateTimeFormatter formatterEST = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTimeEST = nowEST.format(formatterEST);
            // parse the formatted date strings into LocalDateTime objects
            LocalDateTime dateTime1 = LocalDateTime.parse(formattedDateTime, formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(formattedDateTimeEST, formatterEST);
            // calculate the time difference between the two LocalDateTime objects
            long hoursDifference = ChronoUnit.HOURS.between(dateTime1, dateTime2);
//        System.out.println(hoursDifference + " hours difference.");
            LocalTime start_Time_Est = start_Time.plusHours(hoursDifference);
            LocalTime end_Time_Est = end_Time.plusHours(hoursDifference);


            ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments(); //will hold all the appointments to be checked for errors
            boolean errorFlag = false; //used to make sure appointment isn't saved when a time overlap is detected

            for (Appointment appointment : appointmentList) { //iterate through each appointment (Appointment object) in the appointmentList

                //ensure that the error checking applies only for the same customer
                if (appointment.getCustomerId() == customer_Id.getCustomerId()) {

                    // start >= x_start && start < x_end
                    if ((startDateTime.isAfter(appointment.getAppointmentStartDateTime()) || startDateTime.isEqual(appointment.getAppointmentStartDateTime())) &&
                            startDateTime.isBefore(appointment.getAppointmentEndDateTime())) {
                        errorFlag = true;
                        // end > x_start && end <= x_end
                    } else if (endDateTime.isAfter(appointment.getAppointmentStartDateTime()) &&
                            (endDateTime.isBefore(appointment.getAppointmentEndDateTime()) || endDateTime.isEqual(appointment.getAppointmentEndDateTime()))) {
                        errorFlag = true;
                        // start <= x_start && end >= x_end
                    } else if ((startDateTime.isBefore(appointment.getAppointmentStartDateTime()) || startDateTime.isEqual(appointment.getAppointmentStartDateTime())) &&
                            (endDateTime.isAfter(appointment.getAppointmentEndDateTime()) || endDateTime.isEqual(appointment.getAppointmentEndDateTime()))) {
                        errorFlag = true;
                    } else{
                        errorFlag = false;
                    }
                }
            }

            if (errorFlag == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR); //error message displays
                alert.setTitle("Date/Time Error");
                alert.setHeaderText("Date/Time Error");
                alert.setContentText("Times selected overlap with another appointment.");
                alert.showAndWait();
            } else{
                //ensuring the start and end times fall within the business hours
                if (start_Time_Est.isBefore(businessStart) || start_Time_Est.isAfter(businessEnd) || end_Time_Est.isBefore(businessStart) || end_Time_Est.isAfter(businessEnd)){
                    Alert alert = new Alert(Alert.AlertType.ERROR); //error message displays
                    alert.setTitle("Time Error");
                    alert.setHeaderText("Time Error");
                    alert.setContentText("The hours selected do not fit within the business hours.");
                    alert.showAndWait();
                } else {
                    if (endDateTime.isBefore(startDateTime)){ //if the end date/time comes before the start date/time
                        Alert alert = new Alert(Alert.AlertType.ERROR); //error message displays
                        alert.setTitle("Date/Time Error");
                        alert.setHeaderText("Date/Time Error");
                        alert.setContentText("Ensure the end date/time comes after the start date/time.");
                        alert.showAndWait();
                    } else{
                        //ensure that appointments can't skirt business hours by being longer than a day
                        Duration duration = Duration.between(startDateTime, endDateTime);
                        if (duration.toHours() >= 10) {
                            Alert alert = new Alert(Alert.AlertType.ERROR); //error message displays
                            alert.setTitle("Time Error");
                            alert.setHeaderText("Time Error");
                            alert.setContentText("Appointments must be less than 10 hours.");
                            alert.showAndWait();
                        } else{
                            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()) { //ensure form doesn't have empty fields
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Form Error");
                                alert.setHeaderText("Form Error");
                                alert.setContentText("No fields can be left blank.");
                                alert.showAndWait();
                            } else { //else add the appointment to the database and go to the mainscreen
                                AppointmentDAO.insertAppointment(title, description, location, type, startDateTime, endDateTime, customer_Id.getCustomerId(), user_Id.getUserId(), contact_Id.getContactId());

                                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
                                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
                                Scene scene = new Scene(root, 1040, 630); //create new scene
                                stage.setTitle("Main Screen"); // set title of new scene
                                stage.setScene(scene); //set new scene to the stage
                                stage.show(); //show new scene on the stage
                            }
                        }
                    }
                }
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
     * Discard the appointment to be added and takes the user back to the mainscreen.
     * @param actionEvent when the cancel button is pressed
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
        Scene scene = new Scene(root, 1040, 630); //create new scene
        stage.setTitle("Main Screen"); // set title of new scene
        stage.setScene(scene); //set new scene to the stage
        stage.show(); //show new scene on the stage
    }
}
