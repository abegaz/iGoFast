package edu.ung.mccb.csci.csci3300.iGoFast.Controller;

import edu.ung.mccb.csci.csci3300.iGoFast.Model.Admin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    TextField txtadminID, txtusername, txtpassword, txtcpassword;
    static final String AlphaNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom randomSalt = new SecureRandom();

    String genreateRandomSalt(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AlphaNum.charAt(randomSalt.nextInt(AlphaNum.length())));
        return sb.toString();
    }

    public void touchLogin(ActionEvent actionEvent) throws Exception {

        Admin model = new Admin();
        String userPassword = txtpassword.getText();
        String confirmUserPassword = txtcpassword.getText();
        boolean isValid = validatePassword(userPassword, confirmUserPassword);
        if (isValid) {
            String salt = genreateRandomSalt(100);
            String hashAndSaltedPassword = model.generateSaltedHashedPassword(userPassword, salt);

            String adminID = generateAdminID();
            int result = model.saveUserIntoDatabase(adminID, txtusername.getText(), hashAndSaltedPassword, salt);
            //System.out.println("The salted hash code for the plaintext " + txtpassword.getText() + " is " + hashAndSaltedPassword);
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/login.fxml"));
            primaryStage.setTitle("Admin Login");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();
        } else {
            message();
        }

    }

    public String generateAdminID() {
        String randomValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) {
            int index = (int) (rnd.nextFloat() * randomValues.length());
            salt.append(randomValues.charAt(index));
        }
        String ID = txtusername.getText() + salt.toString();

        System.out.println("Your Admin ID is " + ID + "\nYou Will Need This For Future Logins");
        return ID;
    }

    private static boolean validatePassword(String password, String cPassword) {
        if (password.equals(cPassword)) {
            // Regular expression to validate password.
            if ((password.matches("^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$")))
                return true;
            else
                return false;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password mismatch");
            alert.setHeaderText("Please re-enter the password");
            alert.setContentText("The Password must mach, please Please re-enter the password");
            return false;
        }
    }

    private void message() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Requirements");
        alert.setHeaderText("The password entered here  is invalid");
        alert.setContentText("The Password should be at least 8 characters long.\n" +
                "Password should contain at least one lowercase letter .\n" +
                "Password should contain at least one uppercase letter .\n" +
                "Password should contain at least one digit .\n" +
                "Password should have at least special character.\n ");


        alert.showAndWait();

    }

    public void authenticateUser(ActionEvent actionEvent) throws Exception {
        Admin model = new Admin();
        boolean isRegistered = model.verifyLogin(txtpassword.getText(), txtadminID.getText());
        if (isRegistered) {
            //Link to the traffic simulation view
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/road.fxml"));
            primaryStage.setTitle("Traffic Control Simulation");
            primaryStage.setScene(new Scene(root, 905, 603));
            primaryStage.show();
            System.out.println("Login Successful. Welcome.");
        }
    }

    @FXML
    public void goRegister(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/register.fxml"));
            Scene register_page = new Scene(root);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(register_page);
            app_stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
