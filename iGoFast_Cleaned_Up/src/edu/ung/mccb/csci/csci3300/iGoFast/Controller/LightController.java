package edu.ung.mccb.csci.csci3300.iGoFast.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

public class LightController
{
    public LightController(){

    }
    @FXML
    Circle northGreen, southGreen, eastGreen, westGreen, northRed, southRed, eastRed, westRed, northYellow, southYellow, eastYellow, westYellow;
    @FXML
    Rectangle northSensor, southSensor, eastSensor, westSensor;
    @FXML
    Rectangle car1, car2, car3, car4, car5, car6, car7, car8, car9, car10, car11, car12, car13, car14, car15, car16;
    @FXML
    Rectangle northSensorAfter, southSensorAfter, eastSensorAfter, westSensorAfter;

    Timeline northToSouth = new Timeline();
    Timeline northToSouth2 = new Timeline();
    Timeline northToSouth3 = new Timeline();
    Timeline northToSouth4 = new Timeline();
    Timeline southToNorth = new Timeline();
    Timeline southToNorth2 = new Timeline();
    Timeline southToNorth3 = new Timeline();
    Timeline southToNorth4 = new Timeline();
    Timeline eastToWest = new Timeline();
    Timeline eastToWest2 = new Timeline();
    Timeline eastToWest3 = new Timeline();
    Timeline eastToWest4 = new Timeline();
    Timeline westToEast = new Timeline();
    Timeline westToEast2 = new Timeline();
    Timeline westToEast3 = new Timeline();
    Timeline westToEast4 = new Timeline();

    Rectangle currCarW;
    Rectangle currCarN;
    Rectangle currCarE;
    Rectangle currCarS;

    Queue<Rectangle> laneW = new LinkedList<>();
    Queue<Rectangle> laneN = new LinkedList<>();
    Queue<Rectangle> laneE = new LinkedList<>();
    Queue<Rectangle> laneS = new LinkedList<>();




    int CounterW = 0;
    int CounterN = 0;
    int CounterE = 0;
    int CounterS = 0;


    public void start(){
        spawnRandW();
        spawnRandE();
        spawnRandN();
        spawnRandS();
        simulation();
    }
    public void spawnRandW(){
        //spawn cars in West lane
        int num = gen_num();
        CounterW = num;
        System.out.println("W "+num);
        if(num>=1){
            car1.relocate(0,295);
            car1.setOpacity(1.0);
            laneW.add(car1);
            presentW = true;
        }
        if(num>=2){
            car2.relocate(-45, 295);
            car2.setOpacity(1.0);
            laneW.add(car2);
        }
        if(num>=3){
            car3.relocate(-90, 295);
            car3.setOpacity(1.0);
            laneW.add(car3);
        }
        if(num>=4){
            car4.relocate(-135, 295);
            car4.setOpacity(1.0);
            laneW.add(car4);
        }
    }
    public void spawnRandE(){
        //spawn cars in East lane
        int num = gen_num();
        CounterE = num;
        System.out.println("E "+num);
        if(num>=1){
            car5.relocate(864,219);
            car5.setOpacity(1.0);
            laneE.add(car5);
            presentE = true;
        }
        if(num>=2){
            car6.relocate(909, 219);
            car6.setOpacity(1.0);
            laneE.add(car6);
        }
        if(num>=3){
            car7.relocate(954, 219);
            car7.setOpacity(1.0);
            laneE.add(car7);
        }
        if(num>=4){
            car8.relocate(999, 219);
            car8.setOpacity(1.0);
            laneE.add(car8);
        }
    }
    public void spawnRandN(){
        //spawn cars in North lane
        int num = gen_num();
        CounterN = num;
        System.out.println("N "+num);
        if(num>=1){
            car9.relocate(362,14);
            car9.setOpacity(1.0);
            laneN.add(car9);
            presentN = true;
        }
        if(num>=2){
            car10.relocate(362, 59);
            car10.setOpacity(1.0);
            laneN.add(car10);
        }
        if(num>=3){
            car11.relocate(362, 104);
            car11.setOpacity(1.0);
            laneN.add(car11);
        }
        if(num>=4){
            car12.relocate(362, 149);
            car12.setOpacity(1.0);
            laneN.add(car12);
        }
    }
    public void spawnRandS(){
        //spawn cars in South lane
        int num = gen_num();
        CounterS = num;
        System.out.println("S "+num);
        if(num>=1){
            car13.relocate(451,559);
            car13.setOpacity(1.0);
            laneS.add(car13);
            presentS = true;
        }
        if(num>=2){
            car14.relocate(451, 604);
            car14.setOpacity(1.0);
            laneS.add(car14);
        }
        if(num>=3){
            car15.relocate(451, 649);
            car15.setOpacity(1.0);
            laneS.add(car15);
        }
        if(num>=4){
            car16.relocate(451, 694);
            car16.setOpacity(1.0);
            laneS.add(car16);
        }
    }

    Random rand = new Random();
    boolean emergen = false;
    public int gen_num(){
        int  num = rand.nextInt(100);
        if(num<5){
            if(emergen=false) {
                return -1;
            }else{
                gen_num();
            }
        }else if(num<25){
            return 0;
        }else if(num<50){
            return 1;
        }else if(num<75){
            return 2;
        }else if(num<85){
            return 3;
        }else if(num<100){
            return 4;
        }
        return 0;
    }


    @FXML
    public void simulation(){
        keepChecking();
        if(active == false){
            if(CounterN>0) {
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=northToSouth;
                carTimeline[1]=northToSouth2;
                carTimeline[2]=northToSouth3;
                carTimeline[3]=northToSouth4;
                for (int i = 0; i < CounterN; i++) {
                    currCarN = laneN.remove();
                    carTimeline[i].stop();
                    carTimeline[i].setCycleCount(Timeline.INDEFINITE);
                    carTimeline[i].setAutoReverse(false);
                    carTimeline[i].getKeyFrames().add(new KeyFrame(Duration.millis(10000),
                            new KeyValue(currCarN.translateYProperty(), 540)));
                    carTimeline[i].play();
                }
            }
            if(CounterE>0) {
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=eastToWest;
                carTimeline[1]=eastToWest2;
                carTimeline[2]=eastToWest3;
                carTimeline[3]=eastToWest4;
                for (int i = 0; i < CounterE; i++) {
                    currCarE = laneE.remove();
                    carTimeline[i].stop();
                    carTimeline[i].setCycleCount(Timeline.INDEFINITE);
                    carTimeline[i].setAutoReverse(false);
                    carTimeline[i].getKeyFrames().add(new KeyFrame(Duration.millis(30000),
                            new KeyValue(currCarE.translateXProperty(), -864)));
                    carTimeline[i].play();
                }
            }
            if(CounterS>0) {
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=southToNorth;
                carTimeline[1]=southToNorth2;
                carTimeline[2]=southToNorth3;
                carTimeline[3]=southToNorth4;
                for (int i = 0; i < CounterS; i++) {
                    currCarS = laneS.remove();
                    carTimeline[i].stop();
                    carTimeline[i].setCycleCount(Timeline.INDEFINITE);
                    carTimeline[i].setAutoReverse(false);
                    carTimeline[i].getKeyFrames().add(new KeyFrame(Duration.millis(10000),
                            new KeyValue(currCarS.translateYProperty(), -540)));
                    carTimeline[i].play();
                }
            }
            if(CounterW>0) {
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=westToEast;
                carTimeline[1]=westToEast2;
                carTimeline[2]=westToEast3;
                carTimeline[3]=westToEast4;
                for (int i = 0; i < CounterW; i++) {
                    currCarW = laneW.remove();
                    carTimeline[i].stop();
                    carTimeline[i].setCycleCount(Timeline.INDEFINITE);
                    carTimeline[i].setAutoReverse(false);
                    carTimeline[i].getKeyFrames().add(new KeyFrame(Duration.millis(15000),
                            new KeyValue(currCarW.translateXProperty(), 864)));
                    carTimeline[i].play();
                }
            }
        }
    }



    boolean active = false;
    @FXML
    public void ChangeLight()
    {
        if(northGreen.getOpacity() == 1.0){
            greenToYellow_NS();

            yellowTimer_NS();
        }
        else if (northRed.getOpacity() == 1.0){

            greenToYellow_EW();

            yellowTimer_EW();
        }
    }

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
    }

    public void yellowToRed_EW() {
        eastYellow.setOpacity(0.0);
        westYellow.setOpacity(0.0);
        eastRed.setOpacity(1.0);
        westRed.setOpacity(1.0);
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
    }

    public void yellowTimer_NS()
    {
        timer_NS();

    }

    public void yellowTimer_EW()
    {

        timer_EW();

    }

    // timer, timer_EW, and timer_NS control the yellow light duration
    Timer timer = new Timer();

    public void timer_EW()
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
                    yellowToRed_NS();
                    short_timer_EW();
                }
            }

        };

        timer.schedule(task, 0, 2500);
    }

    // one_timer, short_timer_EW, and short_timer_NS control the pause before the next set of lights turn green
    Timer one_timer = new Timer();

    public void short_timer_EW()
    {
        TimerTask task = new TimerTask()
        {

            private final int delay = 1;
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

            private final int delay = 1;
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


    // LoopTimer and loop_method are the master timers for the entire system. They set the time until the next light change
    // This timer must be manipulated based on traffic patterns
    Timer loopTimer = new Timer();

    public void keepChecking()
    {

        TimerTask task = new TimerTask()
        {
            private final int delay = 0;
            private int start = 1;
            @Override
            public void run()
            {
                if(start > delay)
                {
                    start++;
                    carPresent();
                    carsGo();
                    sensorAfterLight();
                    manipulateFlow();
                }
                else {
                    cancel();
                }
            }

        };



        loopTimer.schedule(task, 0, 1000);
    }
    public boolean started = false;

    public boolean noneW = false, noneE = false, noneN = false, noneS = false;


    public void sensorAfterLight()
    {
        double yNS = northSensorAfter.getLayoutY(), ySS = southSensorAfter.getLayoutY(), xES = eastSensorAfter.getLayoutX(), xWS = westSensorAfter.getLayoutX();


        if((car12.getTranslateY() > yNS && car12.getTranslateY() < (yNS + northSensor.getHeight())) && CounterN == 4)
        {
            noneN = true;
            System.out.println("4 from north gone");
        }
        else if((car11.getTranslateY() > yNS && car11.getTranslateY() < (yNS + northSensor.getHeight())) && CounterN == 3)
        {
            noneN = true;
            System.out.println("3 from north gone");
        }
        else if((car10.getTranslateY() > yNS && car10.getTranslateY() < (yNS + northSensor.getHeight())) && CounterN == 2)
        {
            noneN = true;
            System.out.println("2 from north gone");
        }
        else if((car9.getTranslateY() > yNS && car9.getTranslateY() < (yNS + northSensor.getHeight())) && CounterN == 1)
        {
            noneN = true;
            System.out.println("1 from north gone");
        }
        else
        {
            if (CounterN == 0)
                noneN = true;
            else if(presentN == true)
            {
                noneN = false;
            }
        }




        if(((car16.getLayoutY() + car16.getTranslateY()) > ySS && (car16.getLayoutY() + car16.getTranslateY()) < (ySS + southSensor.getHeight())) && CounterS == 4)
        {
            noneS = true;
            System.out.println("4 from south gone");
        }
        else if(((car15.getLayoutY() + car15.getTranslateY()) > ySS && (car14.getLayoutY() + car14.getTranslateY()) < (ySS + southSensor.getHeight())) && CounterS == 3)
        {
            noneS = true;
            System.out.println("3 from south gone");
        }
        else if(((car14.getLayoutY() + car14.getTranslateY()) > ySS && (car14.getLayoutY() + car14.getTranslateY()) < (ySS + southSensor.getHeight())) && CounterS == 2)
        {
            noneS = true;
            System.out.println("2 from south gone");
        }
        else if(((car13.getLayoutY() + car13.getTranslateY()) > ySS && (car13.getLayoutY() + car13.getTranslateY()) < (ySS + southSensor.getHeight())) && CounterS == 1)
        {
            noneS = true;
            System.out.println("1 from south gone");
        }
        else
        {
            if (CounterS == 0)
                noneS = true;
            else if(presentS == true)
            {
                noneS = false;
            }
        }




        if(((car8.getLayoutX() + car8.getTranslateX()) > xES && (car8.getLayoutX() + car8.getTranslateX()) < (xES + eastSensor.getWidth())) && CounterE == 4)
        {
            noneE = true;
            System.out.println("4 from east gone");
        }
        else if(((car7.getLayoutX() + car7.getTranslateX()) > xES && (car7.getLayoutX() + car7.getTranslateX()) < (xES + eastSensor.getWidth())) && CounterE == 3)
        {
            noneE = true;
            System.out.println("3 from east gone");
        }
        else if(((car6.getLayoutX() + car6.getTranslateX()) > xES && (car6.getLayoutX() + car6.getTranslateX()) < (xES + eastSensor.getWidth())) && CounterE == 2)
        {
            noneE = true;
            System.out.println("2 from east gone");
        }
        else if(((car5.getLayoutX() + car5.getTranslateX()) > xES && (car5.getLayoutX() + car5.getTranslateX()) < (xES + eastSensor.getWidth())) && CounterE == 1)
        {
            noneE = true;
            System.out.println("1 from east gone");
        }
        else
            {
                if (CounterE == 0)
                    noneE = true;
                else if(presentE == true)
                {
                    noneE = false;
                }
            }



        if((car4.getTranslateX() > xWS && car4.getTranslateX() < (xWS + westSensor.getWidth())) && CounterW == 4)
        {
            noneW = true;
            System.out.println("4 from west gone");
        }
        else if((car3.getTranslateX() > xWS && car3.getTranslateX() < (xWS + westSensor.getWidth())) && CounterW == 3)
        {
            noneW = true;
            System.out.println("3 from west gone");

        }
        else if((car2.getTranslateX() > xWS && car2.getTranslateX() < (xWS + westSensor.getWidth())) && CounterW == 2)
        {
            noneW = true;
            System.out.println("2 from west gone");

        }
        else if((car1.getTranslateX() > xWS && car1.getTranslateX() < (xWS + westSensor.getWidth())) && CounterW == 1)
        {
            noneW = true;
            System.out.println("1 from west gone");

        }
        else
        {
            if (CounterW == 0)
                noneW = true;
            else if(presentW == true)
            {
                noneW = false;
            }
        }
    }

    


    // Constantly called checking for a condition to be met
    public void manipulateFlow()
    {

        if(noneN == true && noneS == true && noneE == false && noneW == false && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight();
        } else if(noneN == false && noneS == true && noneE == false && noneW == false && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if(noneN == true && noneS == false && noneE == false && noneW == false && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight();
        }


        else if (noneN == false && noneS == false && noneE == true && noneW == true && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == false && noneS == false && noneE == false && noneW == true && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == false && noneS == false && noneE == true && noneW == false && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }


        else if (noneN == true && noneS == true && noneE == false && noneW == true && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == true && noneS == true && noneE == true && noneW == false && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == false && noneS == true && noneE == true && noneW == true && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == true && noneS == false && noneE == true && noneW == true && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }


        else if (noneN == false && noneS == true && noneE == false && noneW == true && (presentN == true || presentS == true) && westGreen.getOpacity() == 1)
        {
            ChangeLight();
        }
        else if (noneN == true && noneS == false && noneE == true && noneW == false && (presentE == true || presentW == true) && northGreen.getOpacity() == 1)
        {
            ChangeLight(); b
        }


    // create a timer that calls this method every second checking for if a car is at a light
    boolean presentN = false, presentS = false, presentE = false, presentW = false;



    public void carPresent()
    {
        double yNS = northSensor.getLayoutY(), ySS = southSensor.getLayoutY(), xES = eastSensor.getLayoutX(), xWS = westSensor.getLayoutX();

        if(CounterN>0){
            if((car9.getTranslateY() > yNS && car9.getTranslateY() < (yNS + northSensor.getHeight())) && (northRed.getOpacity() == 1.0 || northYellow.getOpacity() == 1.0)){
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=northToSouth;
                carTimeline[1]=northToSouth2;
                carTimeline[2]=northToSouth3;
                carTimeline[3]=northToSouth4;
                for(int i = 0; i<CounterN; i++){
                    carTimeline[i].pause();
                    laneN.add(currCarN);
                    presentN = true;
                    noneN = false;
                    System.out.println("Car present at North light.");
                }
            }
        }
        if(CounterS>0){
            if(((car13.getLayoutY() + car13.getTranslateY()) > ySS && (car13.getLayoutY() + car13.getTranslateY()) < (ySS + southSensor.getHeight())) && (southRed.getOpacity() == 1.0 || southYellow.getOpacity() == 1.0)){
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=southToNorth;
                carTimeline[1]=southToNorth2;
                carTimeline[2]=southToNorth3;
                carTimeline[3]=southToNorth4;
                for(int i = 0; i<CounterS; i++){
                    carTimeline[i].pause();
                    laneS.add(currCarS);
                    presentS = true;
                    noneS = false;
                    System.out.println("Car present at South light.");
                }
            }
        }
        if(CounterE>0){
            if(((car5.getLayoutX() + car5.getTranslateX()) > xES && (car5.getLayoutX() + car5.getTranslateX()) < (xES + eastSensor.getWidth())) && (eastRed.getOpacity() == 1.0 || eastYellow.getOpacity() == 1.0)){
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=eastToWest;
                carTimeline[1]=eastToWest2;
                carTimeline[2]=eastToWest3;
                carTimeline[3]=eastToWest4;
                for(int i = 0; i<CounterE; i++){
                    carTimeline[i].pause();
                    laneE.add(currCarE);
                    presentE = true;
                    noneE = false;
                    System.out.println("Car present at East light.");
                }
            }
        }
        if(CounterW>0)
        {
            if((car1.getTranslateX() > xWS && car1.getTranslateX() < (xWS + westSensor.getWidth())) && (westRed.getOpacity() == 1.0 || westYellow.getOpacity() == 1.0)){
                Timeline carTimeline[] = new Timeline[4];
                carTimeline[0]=westToEast;
                carTimeline[1]=westToEast2;
                carTimeline[2]=westToEast3;
                carTimeline[3]=westToEast4;
                for(int i = 0; i<CounterW; i++){
                    carTimeline[i].pause();
                    laneW.add(currCarW);
                    presentW = true;
                    noneW = false;
                    System.out.println("Car present at West light.");
                }
            }
        }
    }

    public void carsGo()
    {
        if(presentN == true && northGreen.getOpacity() == 1.0)
        {
            northToSouth.play();
            northToSouth2.play();
            northToSouth3.play();
            northToSouth4.play();

            presentN = false;
        }

        if (presentE == true && eastGreen.getOpacity() == 1.0)
        {
            eastToWest.play();
            eastToWest2.play();
            eastToWest3.play();
            eastToWest4.play();

            presentE = false;
        }
        if (presentW == true && westGreen.getOpacity() == 1.0)
        {
            westToEast.play();
            westToEast2.play();
            westToEast3.play();
            westToEast4.play();

            presentW = false;
        }
        if (presentS == true && southGreen.getOpacity() == 1.0)
        {
            southToNorth.play();
            southToNorth2.play();
            southToNorth3.play();
            southToNorth4.play();


            presentS = false;
        }
    }





}
