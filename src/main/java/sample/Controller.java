package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DirectoryStream<Path> ds;

    private ObservableList<Path> filesRight = FXCollections.observableArrayList();
    private ObservableList<Path> filesLeft = FXCollections.observableArrayList();

    private Path currentLeft = Paths.get("C:\\");
    private Path currentRight = Paths.get("C:\\");

    @FXML
    private Label labelRight;

    @FXML
    private Label labelLeft;

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

        defaultLeft();
        defaultRight();

    }

    private void defaultLeft() {
        currentLeft = Paths.get("C:\\");
        labelLeft.setText(currentLeft.toString());
        showDir(currentLeft, Pane.LEFT);
        leftPane.setItems(filesLeft);
    }

    private void defaultRight() {
        currentRight = Paths.get("C:\\");
        labelRight.setText(currentRight.toString());
        showDir(currentRight, Pane.RIGHT);
        rightPane.setItems(filesRight);
    }

    private void showDir(Path dir, Pane pane) {

        switch (pane) {

            case LEFT:

                updateLeft(dir);

                break;

            case RIGHT:

                updateRight(dir);

                break;
        }

    }

    private void updateRight(Path dir) {
        try {
            filesRight.clear();

            ds = Files.newDirectoryStream(dir);
            ds.forEach(p -> {
                filesRight.add(p);
            });

            labelRight.setText(dir.toString());
            currentRight = dir;

        } catch (AccessDeniedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Access denied!");
            alert.showAndWait();

            defaultRight();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void updateLeft(Path dir) {
        try {
            filesLeft.clear();

            ds = Files.newDirectoryStream(dir);
            ds.forEach(p -> {
                filesLeft.add(p);
            });

        } catch (AccessDeniedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Access denied!");
            alert.showAndWait();

            defaultLeft();

        } catch (IOException e) {
            e.printStackTrace();
        }

        labelLeft.setText(dir.toString());
        currentLeft = dir;
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

        if (mouseEvent.getClickCount() == 2) {

            Path selectedPath = rightPane.getSelectionModel().getSelectedItem();

            if (Files.isDirectory(selectedPath)) {
                showDir(selectedPath, Pane.RIGHT);
            }
        }
    }

    public void stepIntoLeft(MouseEvent mouseEvent) {

        if (mouseEvent.getClickCount() == 2) {

            Path selectedPath = leftPane.getSelectionModel().getSelectedItem();

            if (Files.isDirectory(selectedPath)) {
                showDir(selectedPath, Pane.LEFT);
            }
        }
    }

    public void goUpLeft(ActionEvent actionEvent) {

        if (currentLeft.getParent() != null) {
            showDir(currentLeft.getParent(), Pane.LEFT);
        }
    }

    public void goUpRight(ActionEvent actionEvent) {

        if (currentRight.getParent() != null) {
            showDir(currentRight.getParent(), Pane.RIGHT);
        }
    }
}
