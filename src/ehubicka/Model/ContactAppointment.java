package ehubicka.Model;

import javafx.fxml.FXML;

import java.time.LocalDateTime;

/**
 * Used in the second report for populating the type combo box and returning various other appointment details.
 */
public class ContactAppointment { //used for report 2
    @FXML
    private int appointmentId;
    @FXML
    private String appointmentTitle;
    @FXML
    private String appointmentDescription;
    @FXML
    private String appointmentType;
    @FXML
    private LocalDateTime appointmentStartDateTime;
    @FXML
    private LocalDateTime appointmentEndDateTime;
    @FXML
    private int customerId;
    @FXML
    private String contactName;

    public ContactAppointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentType, LocalDateTime appointmentStartDateTime, LocalDateTime appointmentEndDateTime, int customerId, String contactName) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerId = customerId;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the custom string and not a hash code
        return contactName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public LocalDateTime getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }

    public LocalDateTime getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getContactName() {
        return contactName;
    }
}
