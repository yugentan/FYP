package com.example.fyp.Controllers;

import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Logics.GA.GA;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {
    @FXML
    private Label populationLabel;
    @FXML
    private Slider populationSlider;
    @FXML
    private Label generationLabel;
    @FXML
    private Slider generationSlider;
    @FXML
    private Label chromosomeLabel;
    @FXML
    private VBox outputVbox;

    // Method called on screen load
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the population value to slider initial value on load
        int popVal = (int) populationSlider.getValue();
        populationLabel.setText(Integer.toString(popVal));

        // Set the number of chromosome as defined in resource
        int chromeVal = SingletonSession.getSession().getServices().size();
        chromosomeLabel.setText(Integer.toString(chromeVal));

        // Set the generation value to slider initial value on load
        int genVal = (int) generationSlider.getValue();
        generationLabel.setText(Integer.toString(genVal));

        // Add listener to population Slider
        populationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int popVal = (int) populationSlider.getValue();
                populationLabel.setText(Integer.toString(popVal));
            }
        });

        // Add listener to generation Slider
        generationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int popVal = (int) generationSlider.getValue();
                generationLabel.setText(Integer.toString(popVal));
            }
        });

    }

    public void startGA(ActionEvent e){
        //when no resources is defined
        if(SingletonSession.getSession().getServices().isEmpty() ||SingletonSession.getSession().getServices() == null){
            alertEmptyResource();
            return;
        }
        GA.run((int)populationSlider.getValue(), (int)generationSlider.getValue(), outputVbox);


    }
    private void alertEmptyResource(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Resource");
        alert.setHeaderText("Please create a resource via Resource tab");
        alert.showAndWait();
    }

}
