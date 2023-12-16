package com.example.dictionary.ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


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
