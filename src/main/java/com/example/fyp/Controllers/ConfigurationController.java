package com.example.fyp.Controllers;

import com.example.fyp.Logics.AwsManager.ResourceHandler;
import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Logics.Simulation.Node.INode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import software.amazon.awssdk.regions.Region;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {
    @FXML
    private Label instanceListLabel;
    @FXML
    private ChoiceBox<String> regionChoiceBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> regions = new ArrayList<>();
        if(SingletonSession.getSession().getElite()!=null){
            String list = "";
            for(INode node : SingletonSession.getSession().getElite()){
                list += node.getVmType() + " ";
            }
            instanceListLabel.setText(list);
        }
        for(Region r : Region.regions()){
            regions.add(r.toString());
        }
        regionChoiceBox.getItems().addAll(regions);
        regionChoiceBox.setValue("ap-southeast-1");

    }
    public void pushAction(ActionEvent e){
        if(SingletonSession.getSession().getProvider().equalsIgnoreCase("premise")){
            alertInvalidProvider();
            return;
        }
        if(SingletonSession.getSession().getElite()==null){
            alertEmptyList();
            return;
        }


        for(INode node : SingletonSession.getSession().getElite()){
            ResourceHandler.createEc2(SingletonSession.getSession().getAccessKey(),
                    SingletonSession.getSession().getSecretKey(), node.getVmType());
        }

    }
    private void alertEmptyList(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid instances");
        alert.setHeaderText("Please create an instance via Simulation tab");
        alert.showAndWait();
    }

    private void alertInvalidProvider(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid provider");
        alert.setHeaderText("Please Login as AWS provider");
        alert.setContentText("It seems like you are using on premise");
        alert.showAndWait();
    }


}
