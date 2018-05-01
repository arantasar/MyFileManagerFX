package pl.edu.utp.ar.filemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewController implements ViewOperations {

    private Side currentSide = Side.NULL;

    private ObservableList<String> observableLeft = FXCollections.observableArrayList();

    private ObservableList<String> observableRight = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listLeft;
    @FXML
    private Label labelRight;
    @FXML
    private ListView<String> listRight;
    @FXML
    private Label labelLeft;

    @Override
    public void initialSetup() {

        try {
            Files.newDirectoryStream(Paths.get("C:\\"))
                    .forEach(path -> observableLeft.add(path.getFileName().toString()));
            listLeft.setItems(observableLeft);
            Files.newDirectoryStream(Paths.get("C:\\"))
                    .forEach(path -> observableRight.add(path.getFileName().toString()));
            listRight.setItems(observableRight);
            labelLeft.setText("C:\\");
            labelRight.setText("C:\\");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stepIntoLeft(MouseEvent mouseEvent) {

        currentSide = Side.LEFT;

        String selected = listLeft.getSelectionModel().getSelectedItem();

        if (selected != null) {

            Path path = Paths.get(labelLeft.getText(), "/", selected);
            if (mouseEvent.getClickCount() == 2 && Files.isDirectory(path) && selected != "..") {
                showDir(path.toString());
            } else if (mouseEvent.getClickCount() == 2 && selected == "..") {
                showDir(path.getParent().getParent().toString());
            }
        }
    }

    @Override
    public void stepIntoRight(MouseEvent mouseEvent) {

        currentSide = Side.RIGHT;

        String selected = listRight.getSelectionModel().getSelectedItem();

        if (selected != null) {

            Path path = Paths.get(labelRight.getText(), "/", selected);

            if (mouseEvent.getClickCount() == 2 && Files.isDirectory(path) && selected != "..") {
                showDir(path.toString());
            } else if (mouseEvent.getClickCount() == 2 && selected == "..") {
                showDir(path.getParent().getParent().toString());
            }
        }
    }

    @Override
    public void showDir(String path) {

        if (currentSide == Side.LEFT) {
            try {
                observableLeft.clear();
                if (Paths.get(path).getParent() != null) {
                    observableLeft.add("..");
                }
                Files.newDirectoryStream(Paths.get(path))
                        .forEach(p -> observableLeft.add(p.getFileName().toString()));
            } catch (AccessDeniedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Access denied!");
                alert.showAndWait();

                showDir("C:\\");

            } catch (IOException e) {
                e.printStackTrace();
            }

            listLeft.setItems(observableLeft);
            labelLeft.setText(path);

        } else {

            try {
                observableRight.clear();
                if (Paths.get(path).getParent() != null) {
                    observableRight.add("..");
                }
                Files.newDirectoryStream(Paths.get(path))
                        .forEach(p -> observableRight.add(p.getFileName().toString()));
            } catch (AccessDeniedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Access denied!");
                alert.showAndWait();

                showDir("C:\\");

            } catch (IOException e) {
                e.printStackTrace();
            }

            listRight.setItems(observableRight);
            labelRight.setText(path);
        }
    }

    void refreshCurrent() {

        if (getCurrentSide() == Side.LEFT) {
            showDir(getLabelLeft().getText());
        } else {
            showDir(getLabelRight().getText());
        }
    }

    void refreshOpposite() {

        if (getCurrentSide() == Side.LEFT) {
            try {
                observableRight.clear();

                if (Paths.get(labelRight.getText()).getParent() != null) {
                    observableRight.add("..");
                }

                Files.newDirectoryStream(Paths.get(labelRight.getText()))
                        .forEach(p -> observableRight.add(p.getFileName().toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }

            listRight.setItems(observableRight);

        } else {
            try {
                observableLeft.clear();

                if (Paths.get(labelLeft.getText()).getParent() != null) {
                    observableLeft.add("..");
                }

                Files.newDirectoryStream(Paths.get(labelLeft.getText()))
                        .forEach(p -> observableLeft.add(p.getFileName().toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }

            listLeft.setItems(observableLeft);
        }
    }

    Side getCurrentSide() {
        return currentSide;
    }

    Label getLabelRight() {
        return labelRight;
    }

    Label getLabelLeft() {
        return labelLeft;
    }

    ListView<String> getListLeft() {
        return listLeft;
    }

    ListView<String> getListRight() {
        return listRight;
    }
}