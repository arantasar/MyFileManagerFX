package pl.edu.utp.ar.filemanager;

import javafx.scene.input.MouseEvent;

interface ViewOperations {

    void initialSetup();
    void showDir(String path);
    void stepIntoLeft(MouseEvent mouseEvent);
    void stepIntoRight(MouseEvent mouseEvent);

}
