package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.core.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.dictionary.Application.dictionary;

public class AddWordController extends SwitchPage {
    Alerts alerts = new Alerts();
    @FXML
    private Button browseButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button translateButton;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TextField wordTextField;
    @FXML
    private Label successAlert;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * Sets the 'successAlert' visibility to false.
     */
    @FXML
    private void initialize() {
        // Hides the success alert
        successAlert.setVisible(false);
    }

    /**
     * Called when the save button is clicked in the GUI.
     * Gets info from user input fields and process their addition to the dictionary.
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setSaveButton(ActionEvent event) {
        // Addition confirmation
        Alert alertConfirmation = alerts.alertConfirmation("Add word", "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();

        // Get data from input
        String target = wordTextField.getText();
        byte[] pText = htmlEditor.getHtmlText().getBytes(StandardCharsets.UTF_8);
        String meaning = new String(pText, StandardCharsets.UTF_8);

        // Clean up string
        meaning = meaning.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        meaning = meaning.replace("</body></html>", "");

        if (option.get() == ButtonType.OK) { // User wants to add word
            Word word = new Word(target, meaning);
            String w = dictionary.search(target);
            if (!w.equals("<h1 style=\"text-align: center;\">No word found</h1>")) {// In case of collision

                // Replacement confirmation
                Alert selectionAlert = alerts.alertConfirmation("This word already exists",
                        "Từ này đã tồn tại.\n" + "Bạn có muốn thay thế nghĩa cũ bằng nghĩa mới không?");

                // Custom buttons
                selectionAlert.getButtonTypes().clear();
                selectionAlert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if (selection.get() == ButtonType.OK) {
                    // User wants to replace
                    Application.dictionary.update(target, meaning);
                    showSuccessAlert();
                }
            } else { // No collision
                dictionary.insert(target, meaning);
                showSuccessAlert();
            }

            // Clean up input fields
            wordTextField.setText("");
            htmlEditor.setHtmlText("");
        } else if (option.get() == ButtonType.CANCEL) { // User doesnt want to add word
            alerts.showAlertInfo("Information", "Thay đổi không được công nhận.");
        }
    }


    /**
     * Shows the success alert, then hides it after a certain delay.
     */
    private void showSuccessAlert() {
        successAlert.setVisible(true);
        // automatic hide success alert
        runAfterDelay(() -> successAlert.setVisible(false), 1500);
    }

    /**
     * Runs the designated runnable after delay
     * @param runnable
     * @param delay
     */
    public static void runAfterDelay(Runnable runnable , int delay ) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
