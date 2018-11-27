import edu.ung.mccb.csci.csci3300.iGoFast.Controller.LightController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("edu/ung/mccb/csci/csci3300/iGoFast/View/road.fxml"));
        primaryStage.setTitle("Traffic Control Admin Registration");
        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}