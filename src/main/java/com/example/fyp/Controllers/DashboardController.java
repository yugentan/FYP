package com.example.fyp.Controllers;

import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label viewTitleLabel;

    @FXML
    private StackPane contentArea;


    //When user Clicks logout, brought back to the login screen
    public void logoutPressed(ActionEvent event) throws IOException {
        SingletonSession.destroy();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    // When dashboard first initialize this method will run to set the contentArea to resourcePane
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("ResourcePane.fxml"));
            //Clears the contentArea
            contentArea.getChildren().removeAll();
            //Sets it to the one defined
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Method to call when resource pressed
    public void resourcePressed() throws IOException {
        //When already Resource, don't do anything
        if(viewTitleLabel.getText().equalsIgnoreCase("Resource")) {
            return;
        }
        viewTitleLabel.setText("Resource");
        //gets the loader obj
        Parent root = FXMLLoader.load(Main.class.getResource("ResourcePane.fxml"));
        //Clears the contentArea
        contentArea.getChildren().removeAll();
        //Sets it to the one defined
        contentArea.getChildren().setAll(root);
        return;
    }

    //Method to call when simulation is pressed
    public void simulationPressed() throws IOException {
        //When already Simulation, don't do anything
        if(viewTitleLabel.getText().equalsIgnoreCase("Simulation")) {
            return;
        }
        viewTitleLabel.setText("Simulation");
        Parent root = FXMLLoader.load(Main.class.getResource("SimulationPane.fxml"));
        //Clears the contentArea
        contentArea.getChildren().removeAll();
        //Sets it to the one defined
        contentArea.getChildren().setAll(root);
        return;
    }
    //Method to call when configuration is pressed
    public void configurationPressed() throws IOException {
        //When already Configuration, don't do anything
        if(viewTitleLabel.getText().equalsIgnoreCase("Configuration")) {
            return;
        }
        viewTitleLabel.setText("Configuration");
        Parent root = FXMLLoader.load(Main.class.getResource("ConfigurationPane.fxml"));
        //Clears the contentArea
        contentArea.getChildren().removeAll();
        //Sets it to the one defined
        contentArea.getChildren().setAll(root);
        return;

    }

}
