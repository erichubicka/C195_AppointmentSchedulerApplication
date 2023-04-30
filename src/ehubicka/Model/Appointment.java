package ehubicka.Model;

import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Appointment {
    public int appointmentId;
    @FXML
    private String appointmentTitle;
    @FXML
    private String appointmentDescription;
    @FXML
    private String appointmentLocation;
    @FXML
    public String appointmentType;
    public LocalDateTime appointmentStartDateTime;
    @FXML
    private LocalDateTime appointmentEndDateTime;
    @FXML
    private int customerId;
    @FXML
    private int userId;
    @FXML
    private int contactId;
    @FXML
    private String contactName;
    @FXML
    private String customerName;
    @FXML
    private String userName;

    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                       LocalDateTime appointmentStartDateTime, LocalDateTime appointmentEndDateTime, int customerId, int userId, int contactId, String contactName,
                       String customerName, String userName) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
        this.customerName = customerName;
        this.userName = userName;
    }

    public String getContactName() {
        return contactName;
    }
    public String getUserIdName() {return "["+ userId + "] " + userName;} //used in the modify appointment form
    public String getCustomerIdName() {return "[" + customerId + "] " + customerName;} //used in the modify appointment form

    public LocalTime getStartTime() {return appointmentStartDateTime.toLocalTime();}
    public LocalTime getEndTime() {return appointmentEndDateTime.toLocalTime();}
    public LocalDate getStartDate() {return appointmentStartDateTime.toLocalDate();}
    public LocalDate getEndDate() {return  appointmentEndDateTime.toLocalDate();}

    public int getContactId() {return contactId;}

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }

    public void setAppointmentStartDateTime(LocalDateTime appointmentStartDateTime) {
        this.appointmentStartDateTime = appointmentStartDateTime;
    }

    public LocalDateTime getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }

    public void setAppointmentEndDateTime(LocalDateTime appointmentEndDateTime) {
        this.appointmentEndDateTime = appointmentEndDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
