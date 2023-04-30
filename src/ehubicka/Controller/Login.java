package ehubicka.Controller;

import ehubicka.Database.AppointmentDAO;
import ehubicka.Database.UserDAO;
import ehubicka.Model.Appointment;
import ehubicka.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ehubicka.Database.JDBC;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class serves as the first screen a user sees upon executing the application. Users will be prompted to
 * login using credentials stored in a MySql database. If successful, users will be taken to the mainscreen.
 */
public class Login implements Initializable {
    @FXML
    private Label loginLabel;
    @FXML
    private Label timezoneLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label pwLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label zoneId;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label errorLabel;

    /**
     * Used to initialize the controller with any code that needs to be implemented when this screen is accessed.
     * Text will be translated between French/English depending on the users local language setting. The local
     * zone ID of the user will be detected and displayed.
     * @param url passes the resources associated with the FXML file
     * @param resourceBundle manages location specific resources (strings, various data-types, etc)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println); //list all zoneID's
        ZoneId LocalZoneId = ZoneId.of(TimeZone.getDefault().getID()); //get the local zone ID of the user
        zoneId.setText(String.valueOf(LocalZoneId)); //set the zone ID label on the login screen to the local zone ID

        ResourceBundle rb = ResourceBundle.getBundle("ehubicka.Main/Language", Locale.getDefault());
        //check if the getDefault locale matches any of the Language resource bundles (English or French)
        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
//            System.out.println(rb.getString("LoginLabel") + " " + rb.getString("TimezoneLabel"));
            loginLabel.setText(rb.getString("Login"));
            timezoneLabel.setText(rb.getString("TimezoneLabel"));
            userLabel.setText(rb.getString("UsernameLabel"));
            pwLabel.setText(rb.getString("PasswordLabel"));
            loginButton.setText(rb.getString("Login"));
            exitButton.setText(rb.getString("ExitButton"));
        }
    }

    /**
     * Validates the user login credentials and allowing the user to see the mainscreen if the username and password
     * are valid within the database. If not, alerts will be displayed. Upon successful login, a message will
     * display saying whether an upcoming appointment within 15 minutes is happening or not. Will log all login attempts
     * to an output file recording the outcome of the attempt along with the timestamp. Login error messages
     * will be translated between French/English depending on the users local language setting.
     * @param actionEvent When the login button is clicked.
     * @throws IOException most likely if FXMLLoader.load() can't find the .fxml file or if the output file cannot be written to.
     */
    @FXML
    protected void onLoginClick(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password  = passwordTextField.getText();


// ---------------------------Output to File----------------------------------
        // create filename String variable
        String filename = "login_activity.txt";
        // create FileWriter object to append to an existing output file
        FileWriter fileWriter = new FileWriter(filename,true);
        // create and open file
        PrintWriter outputFile = new PrintWriter(fileWriter);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
// ---------------------------------------------------------------------------

        ObservableList<User> userList = UserDAO.getAllUsers(); //will hold all the users to be checked for login authenticity
        boolean errorFlag = false; //used when alerting on an unsuccessful login attempt

        for (User user : userList) {

            if (username.equals(user.getUserName()) && password.equals(user.getPassword())){
                errorFlag = true;

                // write to file
                outputFile.println("Date:" + currentDate + " Time:" + formattedTime + " Login Attempt Successful By " + username);
                // close the file
                outputFile.close();

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")); //load anchor pane of next screen
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow(); //get stage from action event's button
                Scene scene = new Scene(root, 1040, 630); //create new scene
                stage.setTitle("Main Screen"); // set title of new scene
                stage.setScene(scene); //set new scene to the stage
                stage.show(); //show new scene on the stage

                //Alerts if there is or isn't an appointment within the next 15 minutes of logging in
                ObservableList<Appointment> appointmentMainScreenList = FXCollections.observableArrayList();
                appointmentMainScreenList.clear();
                appointmentMainScreenList = AppointmentDAO.get15MinuteAppointments();
                if (appointmentMainScreenList.size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText("App ID:" + AppointmentDAO.get15MinuteAppointments().get(0).appointmentId + " at " + AppointmentDAO.get15MinuteAppointments().get(0).appointmentStartDateTime + " starts within 15 minutes.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Upcoming Appointments");
                    alert.setHeaderText("No Upcoming Appointments");
                    alert.setContentText("No appointments scheduled to start in the next 15 minutes.");
                    alert.showAndWait();
                }
            }
        }

        if (errorFlag == false){
            ResourceBundle rb = ResourceBundle.getBundle("ehubicka.Main/Language", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
                errorLabel.setText(rb.getString("ErrorLabel"));
            }
            usernameTextField.clear();
            passwordTextField.clear();

            // write to file
            outputFile.println("Date:" + currentDate + " Time:" + formattedTime + " Login Attempt Unsuccessful");
            // close the file
            outputFile.close();
        }
    }

    /**
     * When exit button pressed, exit the application main screen window and close the database connection
     * @param actionEvent when the exit button is pressed
     */
    public void OnActionExit(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}