package ehubicka.Model;

import javafx.fxml.FXML;

public class Contact {
    @FXML
    private int contactId;
    @FXML
    private String contactName;
    @FXML
    private String email;

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    @Override
    public String toString(){ // override the toString method so that the comboboxes (in the add/modify forms) populate with the custom string and not a hash code
        return contactName;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
