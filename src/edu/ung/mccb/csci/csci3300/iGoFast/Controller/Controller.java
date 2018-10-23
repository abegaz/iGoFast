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
import javafx.scene.shape.Rectangle;
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

    String genreateRandomSalt( int len ){
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append( AlphaNum.charAt( randomSalt.nextInt(AlphaNum.length()) ));
        return sb.toString();
    }
    public void touchLogin(ActionEvent actionEvent) throws Exception{

        Admin model = new Admin();
        String userPassword =  txtpassword.getText();
        String confirmUserPassword =  txtcpassword.getText();
        boolean isValid= validatePassword(userPassword, confirmUserPassword);
        if (isValid) {
            String salt = genreateRandomSalt(100);
            String hashAndSaltedPassword = model.generateSaltedHashedPassword(userPassword, salt);

            String adminID = generateAdminID();
            int result = model.saveUserIntoDatabase(adminID, txtusername.getText(), hashAndSaltedPassword, salt);
            //System.out.println("The salted hash code for the plaintext " + txtpassword.getText() + " is " + hashAndSaltedPassword);
            Stage primaryStage= new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/login.fxml"));
            primaryStage.setTitle("Admin Login");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();


        }
        else
        {
            message ();
        }

    }

    public String generateAdminID()
    {
        String randomValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) {
            int index = (int) (rnd.nextFloat() * randomValues.length());
            salt.append(randomValues.charAt(index));
        }
        String ID = salt.toString();

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
    private void message ()
    {
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

    public void authenticateUser(ActionEvent actionEvent) throws Exception{
        Admin model = new Admin();
        boolean isRegistered = model.verifyLogin(txtpassword.getText(), txtadminID.getText());
        if(isRegistered)
        {
            //Link to the traffic simulation view!!!!!!!!!!!!!!!
            Stage primaryStage= new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/road.fxml"));
            primaryStage.setTitle("Traffic Control Simulation");
            primaryStage.setScene(new Scene(root, 600, 500));
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    Circle northGreen, southGreen, eastGreen, westGreen, northRed, southRed, eastRed, westRed, northYellow, southYellow, eastYellow, westYellow;
    @FXML
    public Rectangle car;

    @FXML
    public void ChangeLight() {

        if(northGreen.getOpacity() == 1.0){
            greenToYellow_NS();

            yellowTimer_NS();
        }
        else if (northRed.getOpacity() == 1.0){

            greenToYellow_EW();

            yellowTimer_EW();
        }

        loop_method();
    }

    boolean nsIsGreen;
    boolean ewIsGreen;
    public void greenToYellow_NS() {
        northGreen.setOpacity(0.0);
        southGreen.setOpacity(0.0);
        northYellow.setOpacity(1.0);
        southYellow.setOpacity(1.0);
    }

    public void greenToYellow_EW() {
        eastGreen.setOpacity(0.0);
        westGreen.setOpacity(0.0);
        eastYellow.setOpacity(1.0);
        westYellow.setOpacity(1.0);
    }

    public void yellowToRed_NS() {
        northYellow.setOpacity(0.0);
        southYellow.setOpacity(0.0);
        northRed.setOpacity(1.0);
        southRed.setOpacity(1.0);
        nsIsGreen = false;
    }

    public void yellowToRed_EW() {
        eastYellow.setOpacity(0.0);
        westYellow.setOpacity(0.0);
        eastRed.setOpacity(1.0);
        westRed.setOpacity(1.0);
        ewIsGreen = false;
    }

    public void redToGreen_NS() {
        northRed.setOpacity(0.0);
        southRed.setOpacity(0.0);
        northGreen.setOpacity(1.0);
        southGreen.setOpacity(1.0);
    }

    public void redToGreen_EW() {
        eastRed.setOpacity(0.0);
        westRed.setOpacity(0.0);
        eastGreen.setOpacity(1.0);
        westGreen.setOpacity(1.0);
        ewIsGreen = true;
    }

    public void yellowTimer_NS()
    {
        timer_NS();

    }

    public void yellowTimer_EW()
    {

        timer_EW();

    }


    Timer timer = new Timer();

    public void timer_EW()
    {
        TimerTask task = new TimerTask()
        {

            private final int delay = 3;
            private int start = 0;
            @Override
            public void run()
            {
                if(start < delay)
                {
                    start++;
                }
                else {
                    cancel();
                    yellowToRed_EW();
                    short_timer_NS();
                }
            }

        };

        timer.schedule(task, 0, 2500);
    }


    public void timer_NS()
    {
        TimerTask task = new TimerTask()
        {

            private final int delay = 3;
            private int start = 0;
            @Override
            public void run()
            {
                if(start < delay)
                {
                    start++;
                }
                else {
                    cancel();
                    yellowToRed_NS();
                    short_timer_EW();
                }
            }

        };

        timer.schedule(task, 0, 2500);
    }

    Timer one_timer = new Timer();

    public void short_timer_EW()
    {
        TimerTask task = new TimerTask()
        {

            private final int delay = 2;
            private int start = 0;
            @Override
            public void run()
            {
                if(start < delay)
                {
                    start++;
                }
                else {
                    cancel();
                    redToGreen_EW();
                }
            }

        };

        one_timer.schedule(task, 0, 2000);
    }

    public void short_timer_NS()
    {
        TimerTask task = new TimerTask()
        {

            private final int delay = 2;
            private int start = 0;
            @Override
            public void run()
            {
                if(start < delay)
                {
                    start++;
                }
                else {
                    cancel();
                    redToGreen_NS();
                }
            }

        };

        one_timer.schedule(task, 0, 2000);
    }


    Timer loopTimer = new Timer();

    public void loop_method()
    {
        TimerTask task = new TimerTask()
        {
            private final int delay = 30;
            private int start = 0;
            @Override
            public void run()
            {
                if(start < delay)
                {
                    System.out.println(start);
                    start++;

                    double x = car.getLayoutX();
                    if(ewIsGreen){
                        x=x-22;
                        car.setLayoutX(x);
                    }
                }
                else {
                    cancel();
                    ChangeLight();
                }
            }

        };

        loopTimer.schedule(task, 0, 1000);
    }



}
