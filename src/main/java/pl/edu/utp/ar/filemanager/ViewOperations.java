package pl.edu.utp.ar.filemanager;

import javafx.scene.input.MouseEvent;

public interface ViewOperations {

    void initialSetup();
    void showDir(String path);
    void stepIntoLeft(MouseEvent mouseEvent);
    void stepIntoRight(MouseEvent mouseEvent);

}
