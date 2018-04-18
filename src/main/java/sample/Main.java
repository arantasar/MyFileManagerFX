package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

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

        FileSystem fileSystem = FileSystems.getDefault();
        Path path = Paths.get("C://Python27");

        try {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            paths.forEach(p -> System.out.println(p.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // TESTS END

        launch(args);



    }
}
