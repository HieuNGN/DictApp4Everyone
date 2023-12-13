package com.example.dictionary.ui;
import com.example.dictionary.Application;
import com.example.dictionary.core.DictionaryB;
import com.example.dictionary.core.DictionaryManagement;
import com.example.dictionary.core.Words;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class DarkModeController {

    @FXML
    private Button darkModeButton;

    private boolean isDarkMode = false;

    @FXML
    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;

        if (isDarkMode) {
            enableDarkMode();
        } else {
            disableDarkMode();
        }
    }

    private void enableDarkMode() {
        darkModeButton.setStyle("-fx-background-color: #333; -fx-text-fill: #fff;");
    }

    private void disableDarkMode() {
        darkModeButton.setStyle("-fx-background-color: #fff; -fx-text-fill: #000;");
    }

}
