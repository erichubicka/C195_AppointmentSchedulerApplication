module ehubicka.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ehubicka.Main to javafx.fxml;
    exports ehubicka.Main;
    exports ehubicka.Controller;
    opens ehubicka.Controller to javafx.fxml;
    exports ehubicka.Model;
    opens ehubicka.Model to javafx.fxml;
    exports ehubicka.Database;
    opens ehubicka.Database to javafx.fxml;
}