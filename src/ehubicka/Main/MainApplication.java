package ehubicka.Main;

import ehubicka.Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Loads the login screen as the initial interface to the user upon launch. Will open the MySql database connection
 * and close it as well.
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 225);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    //open and close the connection to the database
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}