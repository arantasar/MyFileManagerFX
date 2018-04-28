package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DirectoryStream<Path> ds;

    private ObservableList<Path> filesRight = FXCollections.observableArrayList();
    private ObservableList<Path> filesLeft = FXCollections.observableArrayList();

    private Path pathLeft = Paths.get("C:\\");
    private Path pathRight = Paths.get("C:\\");

    private Side currentSide;
    private Label currentLabel;
    private Path currentPath;
    private ObservableList<Path> currentFiles;
    private ListView<Path> currentPane;

    @FXML
    public Button goUp;

    @FXML
    private Label labelRight;

    @FXML
    private Label labelLeft;

    @FXML
    private ListView<Path> leftPane;

    @FXML
    private ListView<Path> rightPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setCurrent(Side.LEFT);
        update(Paths.get("C:\\"));
        setCurrent(Side.RIGHT);
        update(Paths.get("C:\\"));
        currentSide = Side.NULL;

    }

    private void update(Path dir) {

        try {
            currentFiles.clear();

            ds = Files.newDirectoryStream(dir);
            ds.forEach(p -> currentFiles.add(p));

            currentPane.setItems(currentFiles);
            currentLabel.setText(dir.toString());
            currentPath = dir;

        } catch (AccessDeniedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Access denied!");
            alert.showAndWait();

            update(Paths.get("C:\\"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(ActionEvent actionEvent) {



    }

    public void newFolder(ActionEvent actionEvent) {

        TextInputDialog prompt = new TextInputDialog();
        prompt.setTitle(null);
        prompt.setContentText("Directory name: ");
        Optional<String> ans = prompt.showAndWait();

        if (ans.isPresent()) {

            try {
                Files.createDirectory(Paths.get(currentPath.toString(), ans.get()));
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("Directory created");
                a.showAndWait();
            } catch (FileAlreadyExistsException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Directory exists!");
                alert.showAndWait();

                update(currentPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            update(currentPath);

        }

    }

    public void newFile(ActionEvent actionEvent) {
        TextInputDialog prompt = new TextInputDialog();
        prompt.setTitle(null);
        prompt.setContentText("Filename: ");
        Optional<String> ans = prompt.showAndWait();

        if (ans.isPresent()) {

            try {
                Files.createFile(Paths.get(currentPath.toString(), ans.get()));
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("File created");
                a.showAndWait();
            } catch (FileAlreadyExistsException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("File exists!");
                alert.showAndWait();

                update(currentPath);

            } catch (IOException e) {
                e.printStackTrace();
            }

            update(currentPath);

        }
    }

    public void delete(ActionEvent actionEvent) {

        Path directoryPath = currentPane.getSelectionModel().getSelectedItem();

        try {
            FileSystemUtils.deleteRecursively(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        update(directoryPath.getParent());
    }

    public void copy(ActionEvent actionEvent) {



    }

    public void stepIntoRight(MouseEvent mouseEvent) {
        if (currentSide != Side.RIGHT) setCurrent(Side.RIGHT);
        if (mouseEvent.getClickCount() == 2) {
            stepInto();
        }
    }

    public void stepIntoLeft(MouseEvent mouseEvent) {
        if (currentSide != Side.LEFT) setCurrent(Side.LEFT);
        if (mouseEvent.getClickCount() == 2) {
            stepInto();
        }
    }

    public void goUp(ActionEvent actionEvent) {
        if (currentPath.getParent() != null) {
            update(currentPath.getParent());
        }
    }

    private void stepInto() {
        Path selectedPath = currentPane.getSelectionModel().getSelectedItem();
        if (Files.isDirectory(selectedPath)) {
            update(selectedPath);
        } else {
            // OTWARCIE PLIKU
        }
    }

    private void setCurrent(Side side) {
        switch (side) {
            case LEFT:
                currentSide = Side.LEFT;
                currentLabel = labelLeft;
                currentPath = Paths.get(labelLeft.getText());
                currentFiles = filesLeft;
                currentPane = leftPane;
                break;
            case RIGHT:
                currentSide = Side.RIGHT;
                currentLabel = labelRight;
                currentPath = Paths.get(labelRight.getText());
                currentFiles = filesRight;
                currentPane = rightPane;
                break;
        }
    }

}
