package com.example.fyp.Controllers;

import com.example.fyp.Logics.AwsManager.Authentication;
import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField accessKeyField;
    @FXML
    private PasswordField secretKeyField;
    @FXML
    private ChoiceBox<String> providerChoiceBox;

    private final String[] providers = {"On Premise", "AWS"};

    // Method called in initialization of login screen, populates choice box
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        providerChoiceBox.getItems().addAll(providers);

        //Set initial selected as on premises, disable access/secret key field
        providerChoiceBox.setValue(providers[0]);
        accessKeyField.setDisable(true);
        secretKeyField.setDisable(true);

        providerChoiceBox.setOnAction(this::getProvider);
    }

    //Event listener for choice box selection
    public void getProvider(ActionEvent event){
        String p = providerChoiceBox.getValue();
        if(!p.equalsIgnoreCase("on premise")){
            accessKeyField.setDisable(false);
            secretKeyField.setDisable(false);
        }else{
            //Disable fields if on premise
            accessKeyField.setDisable(true);
            secretKeyField.setDisable(true);
        }
    }

    public void login(ActionEvent event) throws IOException{
        //Checks if AWS is selected
        if(providerChoiceBox.getValue().equalsIgnoreCase("AWS")){
            awsLogin(event);
        }else{
            //Login flow for on premise
            System.out.println("LOGGING IN PREMISE");
            SingletonSession.getSession().setAccessKey(null);
            SingletonSession.getSession().setSecretKey(null);
            SingletonSession.getSession().setProvider("premise");
            goToDashboard(event);
        }
    }

    // Method to login aws, check for connection first then checks credentials
    private void awsLogin(ActionEvent event) throws IOException{
        if(!isInternetReachable()){
            alertNoInternet();
            return;
        }
        String accessKey = accessKeyField.getText();
        String secretKey = secretKeyField.getText();
        //Checks if aws keys are correct
        if(Authentication.auth(accessKey,secretKey)){
            System.out.println("Login Success");
            SingletonSession.getSession().setAccessKey(accessKey);
            SingletonSession.getSession().setSecretKey(secretKey);
            SingletonSession.getSession().setProvider("aws");
            goToDashboard(event);
        }else{
            //If aws keys are wrong
            alertInvalidCredentials();
        }
    }
    // Method to trigger no internet connection
    private void alertNoInternet(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Internet Connection Failed");
        alert.setHeaderText("Failed to connect to the internet");
        alert.setContentText("Please use the 'On Premise' provider");
        alert.showAndWait();
    }

    // Method to trigger invalid credentials alert
    private void alertInvalidCredentials(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Invalid Access/Secret Key");
        alert.setContentText("If you are having trouble signing in using AWS's access/secret key, " +
                "please use the keys provided in the readme file. Please do not abuse this key as it is " +
                "provided for convenience sake. The keys will be deactivated after grading ");

        alert.showAndWait();
    }

    // Method to check if user is connected to the internet
    private boolean isInternetReachable() {
        try (Socket soc = new Socket()) {
            soc.connect(new InetSocketAddress("www.google.com", 80), 3000);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    // Method to go to dashboard screen
    private void goToDashboard(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
}
