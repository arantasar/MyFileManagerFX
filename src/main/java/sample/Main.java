package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("appGUI.fxml"));

        primaryStage.setTitle("My File Manager FX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();





    }


    public static void main(String[] args) {

        // TESTS



        // TESTS END

        launch(args);



    }
}
