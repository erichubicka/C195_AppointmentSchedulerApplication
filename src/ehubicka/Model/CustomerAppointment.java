package ehubicka.Model;

import javafx.fxml.FXML;

import java.time.LocalDateTime;

/**
 * Used in the third report for populating customers into a combo box and getting their respective appointment
 * details.
 */
public class CustomerAppointment { //used for report 3
    @FXML
    private int appointmentId;
    @FXML
    private LocalDateTime appointmentStartDateTime;
    @FXML
    private LocalDateTime appointmentEndDateTime;
    @FXML
    private String customerName;

    public CustomerAppointment(int appointmentId, LocalDateTime appointmentStartDateTime, LocalDateTime appointmentEndDateTime, String customerName) {
        this.appointmentId = appointmentId;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the custom string and not a hash code
        return customerName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public LocalDateTime getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }

    public LocalDateTime getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }
}
