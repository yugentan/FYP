package com.example.fyp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        try {
            // Adding fxml
            Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
            Scene scene = new Scene(root);

            //Adding css
            //scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
            String css = Main.class.getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //parse lamda function of stage into our logout
            stage.setOnCloseRequest(windowEvent -> {
                windowEvent.consume();
                logout(stage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //This is for pressing the X icon of window
    public void logout(Stage stage){

        //Creating an alert when user clicks logout button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Application Exiting...");
        alert.setHeaderText("You are about to quit!");
        alert.setContentText("Are you sure you want to quit?");


        if(alert.showAndWait().get() == ButtonType.OK){
            // close the window
//          System.out.println("you successfully logout");
            stage.close();
        }

    }


    public static void main(String[] args) {
        launch();
    }
}
