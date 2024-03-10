package com.example.fyp.Controllers;

import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Logics.Simulation.Pods.Pod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.*;

public class ResourceController implements Initializable {
    @FXML
    private VBox resourceVBox;
    @FXML
    private TextField serviceNameField;
    @FXML
    private ChoiceBox<String> mipsChoiceBox;
    @FXML
    private ChoiceBox<String> cpuChoiceBox;
    @FXML
    private TextField storageField;

    private static final String[] availableMips = {"1000", "2000"};
    private static final String[] availableCpus = {"1", "2", "4", "8"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize choice box values
        mipsChoiceBox.getItems().addAll(availableMips);
        cpuChoiceBox.getItems().addAll(availableCpus);

        //Set initial Value
        mipsChoiceBox.setValue(availableMips[0]);
        cpuChoiceBox.setValue(availableCpus[0]);
        getSessionResource();
    }

    public void addDeployment(ActionEvent e){
        //When the service name is defined
        if(checkServiceNameExist(serviceNameField.getText())){
            alertServiceNameExist();
            return;
        }
        if(serviceNameField.getText().equalsIgnoreCase("")){
           alertInvalidServiceName();
           return;
        }
        BorderPane itemPane = getBorderPane();
        // Add some margin for spacing
        VBox.setMargin(itemPane, new Insets(5));
        // Add item to resource tab
        resourceVBox.getChildren().add(itemPane);
        // Add service to session's service
        addServiceToSession();
        //reset form to original val;
        clearField();
    }
    // This method gets the currently save services in session
    private void getSessionResource(){
        List<Pod> pods = SingletonSession.getSession().getServices();
        if(pods.isEmpty())return;
        for(Pod p : pods){
            Label label = new Label(p.getServiceName());
            Button deleteButton = new Button("Delete");
            //Delete action, remove from vbox resource tab and session
            deleteButton.setOnAction(event -> {
                resourceVBox.getChildren().remove(deleteButton.getParent());
                String podToRemove = ((Label)((BorderPane)deleteButton.getParent()).getLeft()).getText();
                removeServiceFromSession(podToRemove);
            });
            //Create a new border pane to be added into resource tab
            BorderPane itemPane = new BorderPane();
            itemPane.setLeft(label); // Label on the left
            itemPane.setRight(deleteButton); // Delete button on the right
            VBox.setMargin(itemPane, new Insets(5));
            resourceVBox.getChildren().add(itemPane);
            System.out.println(p.toString());
        }
    }
    // This method creates a new borderpane to be added into the resource tab
    private BorderPane getBorderPane() {
        Label label = new Label(serviceNameField.getText());
        Button deleteButton = new Button("Delete");
        //Delete action, remove from vbox resource tab and session
        deleteButton.setOnAction(event -> {
            resourceVBox.getChildren().remove(deleteButton.getParent());
            String podToRemove = ((Label)((BorderPane)deleteButton.getParent()).getLeft()).getText();
            removeServiceFromSession(podToRemove);
        });

        //Create a new border pane to be added into resource tab
        BorderPane itemPane = new BorderPane();
        itemPane.setLeft(label); // Label on the left
        itemPane.setRight(deleteButton); // Delete button on the right
        return itemPane;
    }

    // This method clears and reset the resource definition fields
    private void clearField(){
        serviceNameField.clear();
        mipsChoiceBox.setValue(availableMips[0]);
        cpuChoiceBox.setValue(availableCpus[0]);
        storageField.clear();
    }

    // This method adds service to session for simulation later
    private void addServiceToSession(){
        Pod p = new Pod(SingletonSession.getSession().getServices().size(),
                0,
                serviceNameField.getText(),
                Integer.parseInt(mipsChoiceBox.getValue()),
                Integer.parseInt(cpuChoiceBox.getValue()));
        SingletonSession.getSession().getServices().add(p);
    }
    // This method check if service name is in use
    private boolean checkServiceNameExist(String servicename){
        List<Pod> pods = SingletonSession.getSession().getServices();
        for(Pod p : pods){
            if(p.getServiceName().equalsIgnoreCase(servicename)) {
                return true;
            }
        }
        return false;
    }
    // This method remove service from session
    private void removeServiceFromSession(String serviceName){
        SingletonSession.getSession()
                .getServices()
                .removeIf(pod -> pod.getServiceName().equalsIgnoreCase(serviceName));
    }
    // Alert Function for service name exist
    private void alertServiceNameExist(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Service name already in use");
        alert.setHeaderText("The service name you entered already exist");
        alert.setContentText("Please enter a unique service name");
        alert.showAndWait();
    }
    // Alert Function for invalid service name
    private void alertInvalidServiceName(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Service Name");
        alert.setHeaderText("Please enter a service name");
        alert.setContentText("Service name cant be empty");
        alert.showAndWait();
    }
}
