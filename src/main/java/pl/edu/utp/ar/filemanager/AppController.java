package pl.edu.utp.ar.filemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable, ButtonsOperations {

    @FXML
    private ViewController viewController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewController.initialSetup();
    }

    @Override
    public void move(ActionEvent actionEvent) {
        String source, target, sourcePath;

        if (viewController.getCurrentSide() == Side.LEFT) {
            source = viewController.getListLeft().getSelectionModel().getSelectedItem();
            sourcePath = viewController.getLabelLeft().getText() + "\\" + source;
            target = viewController.getLabelRight().getText() + "\\" + source;
        } else {
            source = viewController.getListRight().getSelectionModel().getSelectedItem();
            sourcePath = viewController.getLabelRight().getText() + "\\" + source;
            target = viewController.getLabelLeft().getText() + "\\" + source;
        }

        if (!source.equals("..") &&
                !viewController.getLabelRight().getText().equals(viewController.getLabelLeft().getText())) {

            try {
                FileSystemUtils.copyRecursively(Paths.get(sourcePath), Paths.get(target));
                FileSystemUtils.deleteRecursively(Paths.get(sourcePath));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("This is the same location!");
            alert.show();
        }

        viewController.refreshCurrent();
        viewController.refreshOpposite();
    }

    @Override
    public void copy(ActionEvent actionEvent) {

        String source, target, sourcePath;

        if (viewController.getCurrentSide() == Side.LEFT) {
            source = viewController.getListLeft().getSelectionModel().getSelectedItem();
            sourcePath = viewController.getLabelLeft().getText() + "\\" + source;
            target = viewController.getLabelRight().getText() + "\\" + source;
        } else {
            source = viewController.getListRight().getSelectionModel().getSelectedItem();
            sourcePath = viewController.getLabelRight().getText() + "\\" + source;
            target = viewController.getLabelLeft().getText() + "\\" + source;
        }

        if (!source.equals("..") &&
                !viewController.getLabelRight().getText().equals(viewController.getLabelLeft().getText())) {

            try {
                FileSystemUtils.copyRecursively(Paths.get(sourcePath), Paths.get(target));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("This is the same location!");
            alert.show();
        }

        viewController.refreshOpposite();
    }

    @Override
    public void delete(ActionEvent actionEvent) {

        String selectedItem, selectedPath;

        if (viewController.getCurrentSide() == Side.LEFT) {
            selectedItem = viewController.getListLeft().getSelectionModel().getSelectedItem();
            selectedPath = Objects.equals(viewController.getLabelLeft().getText(), "C:\\") ?
                    viewController.getLabelLeft().getText() + selectedItem :
                    viewController.getLabelLeft().getText() + "\\" + selectedItem;
        } else {
            selectedItem = viewController.getListRight().getSelectionModel().getSelectedItem();
            selectedPath = Objects.equals(viewController.getLabelRight().getText(), "C:\\") ?
                    viewController.getLabelRight().getText() + selectedItem :
                    viewController.getLabelRight().getText() + "\\" + selectedItem;
        }

        if (!Objects.equals(selectedItem, "..")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to delete this item ?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {


                try {
                    FileSystemUtils.deleteRecursively(Paths.get(selectedPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                viewController.refreshCurrent();
            }
        }
    }

    @Override
    public void newFile(ActionEvent actionEvent) {

        TextInputDialog prompt = new TextInputDialog();
        prompt.setTitle(null);
        prompt.setContentText("File name: ");
        Optional<String> ans = prompt.showAndWait();

        if (ans.isPresent()) {

            try {

                if (viewController.getCurrentSide() == Side.LEFT) {
                    Files.createFile(Paths.get(viewController.getLabelLeft().getText(), ans.get()));
                } else {
                    Files.createFile(Paths.get(viewController.getLabelRight().getText(), ans.get()));
                }

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("File created");
                a.showAndWait();

            } catch (FileAlreadyExistsException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("File exists!");
                alert.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                viewController.refreshCurrent();
            }
        }
    }

    @Override
    public void newFolder(ActionEvent actionEvent) {

        TextInputDialog prompt = new TextInputDialog();
        prompt.setTitle(null);
        prompt.setContentText("Directory name: ");
        Optional<String> ans = prompt.showAndWait();

        if (ans.isPresent()) {

            try {

                if (viewController.getCurrentSide() == Side.LEFT) {
                    Files.createDirectory(Paths.get(viewController.getLabelLeft().getText(), ans.get()));
                } else {
                    Files.createDirectory(Paths.get(viewController.getLabelRight().getText(), ans.get()));
                }

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("Directory created");
                a.showAndWait();

            } catch (FileAlreadyExistsException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Directory exists!");
                alert.showAndWait();

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                viewController.refreshCurrent();
            }
        }
    }

}