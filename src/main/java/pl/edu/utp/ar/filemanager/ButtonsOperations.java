package pl.edu.utp.ar.filemanager;

import javafx.event.ActionEvent;

public interface ButtonsOperations {

    void move(ActionEvent actionEvent);

    void newFolder(ActionEvent actionEvent);

    void newFile(ActionEvent actionEvent);

    void delete(ActionEvent actionEvent);

    void copy(ActionEvent actionEvent);
}
