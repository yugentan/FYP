module com.example.fyp {
    //requires jfx related modules
    requires javafx.controls;
    requires javafx.fxml;

    //requires aws related modules
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.ec2;
    requires cloudsim;

    opens com.example.fyp.Controllers to javafx.fxml;


    exports com.example.fyp;
    exports com.example.fyp.Controllers;
}