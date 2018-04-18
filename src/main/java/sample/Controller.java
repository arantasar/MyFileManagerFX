package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    DirectoryStream<Path> ds;
    ObservableList<Path> files = FXCollections.observableArrayList();
    Path c = Paths.get("C:\\Python27");



    @FXML
    private ListView<Path> leftPane;

    @FXML
    private ListView<Path> rightPane;

    @FXML
    private Button btnMove;

    @FXML
    private Button btnCopy;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewFile;

    @FXML
    private Button btnNewFolder;

    @FXML
    private Button btnFilter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(c.getParent() != null) {
            files.add(c.relativize(c.getParent()));
        }

        try {
            ds = Files.newDirectoryStream(c);
            ds.forEach(path -> {
                files.add(path.getFileName());
            });
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        leftPane.setItems(files);
        rightPane.setItems(files);


    }

    public void move(ActionEvent actionEvent) {
        System.out.println("Move clicked");
    }

    public void filter(ActionEvent actionEvent) {
    }

    public void newFolder(ActionEvent actionEvent) {
    }

    public void newFile(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }

    public void copy(ActionEvent actionEvent) {
    }

    public void stepIntoRight(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            System.out.println(rightPane.getSelectionModel().getSelectedItem());
        }
    }

    public void stepIntoLeft(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            System.out.println(leftPane.getSelectionModel().getSelectedItem());
        }
    }
}
