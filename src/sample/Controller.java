package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {
    Timer time = new Timer();
    @FXML
    Circle greenlight, yellowlight, redlight;
    Object light = new Object();
    public void ChangeLight() {
        if (greenlight.getOpacity() == 1) {
            greenlight.setOpacity(0);
            yellowlight.setOpacity(1);
            pause();
            //yellowlight.setOpacity(1);
            yellowlight.setOpacity(0);
            redlight.setOpacity(1);
        } else {
            redlight.setOpacity(0);
            greenlight.setOpacity(1);
        }

    }
    public void schedule(TimerTask ChangeLight, Date time){

    }

    public void pause(){
        try{
            light.wait(300);
            light.notify();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
