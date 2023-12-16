package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController extends SwitchPage implements Initializable {
    @FXML
    private Button gameBtn;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets up the action handlers for the 'btnReturn', 'btnHangman', and 'btnMultipleChoice' buttons.
     * When each button is clicked, it loads a new scene from the corresponding FXML file and sets it on the current stage.
     * If there are any errors during this process, it prints the stack trace.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String path = "fxml/MultipleChoiceGUI.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource(path));
                    Stage stage = (Stage) gameBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
