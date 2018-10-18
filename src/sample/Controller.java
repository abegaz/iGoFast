package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {
    Timer time = new Timer();
    @FXML
    Circle greenlight, yellowlight, redlight;
    public void ChangeLight() {

        if(greenlight.getOpacity()==1){
            greenToYellow();
        }
        else if (yellowlight.getOpacity()==1) {
            try {
                Thread.sleep(3000);
                yellowToRed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if (redlight.getOpacity()==1){
            redToGreen();
        }
    }

    public void greenToYellow() {
        greenlight.setOpacity(0);
        yellowlight.setOpacity(1);
    }
    public void yellowToRed() {
        yellowlight.setOpacity(0);
        redlight.setOpacity(1);
    }
    public void redToGreen() {
        redlight.setOpacity(0);
        greenlight.setOpacity(1);
    }

}
