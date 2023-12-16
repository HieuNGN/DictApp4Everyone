package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.core.Trie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.example.dictionary.Application.dictionary;


public class SearchController extends SwitchPage {
    Alerts alerts = new Alerts();
    private String latestWord = "";
    @FXML
    private TextField searchField;
    @FXML
    private WebView definitionView;
    @FXML
    private ListView<String> resultsList;
    @FXML
    private Button addWordButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button deleteWordButton;
    @FXML
    private Button editWordButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button textToSpeechButton;
    @FXML
    private Button exitButton;


    public SearchController() {
    }

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets the focus to the 'searchField' and calls the 'takeSearchList' method.
     */
    @FXML
    public void initialize() {
        // set the focus to the search field
        Platform.runLater(() -> searchField.requestFocus());
        getResults();
    }

    /**
     * Called whenever the user types in the search field.
     * Gets possible results from the database for the current word in the search field.
     * Does NOT display definitions.
     */
    public void getResults() {
        // Clear the resultsList
        resultsList.getItems().clear();
        resultsList.setVisible(true);

        // Search for list of results
        String word = searchField.getText();
        ArrayList<String> results = Trie.search(word);

        // Display results
        for (String w : results) {
            resultsList.getItems().add(w);
        }

        // If there's nothing in the search field, display no definition
        if (searchField.getText().isEmpty()) {
            definitionView.getEngine().loadContent("");
            return;
        }

    }

    /**
     * Called whenever the user clicks on one of the results or presses Enter.
     * Searches for the word in the database and displays the definition.
     */
    @FXML
    public void searchWord() {
        String word = searchField.getText();

        String meaning = dictionary.search(word);

        getResults();

        latestWord = word;
        definitionView.getEngine().loadContent(meaning);
    }

    /**
     * Called whenever the user presses enter.
     * Gets definition of the selected word in the results list or the input field
     * @param key The KeyEvent object representing the key press event.
     */
    @FXML
    public void selectEnter(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            String word = resultsList.getSelectionModel().getSelectedItem();
            searchField.setText(word);
            searchWord();
        }
    }

    /**
     * Called whenever the user clicks on an item in the 'listView'.
     * Gets the item the user clicked on and shows its definition.
     * @param event The MouseEvent object representing the mouse click event.
     */
    @FXML
    public void selectClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
            String word = resultsList.getSelectionModel().getSelectedItem();
            searchField.setText(word);
            searchWord();
        }
    }

    @FXML
    public void setClickOnTextField(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
            resultsList.setVisible(true);
        }
    }

    /**
     * This method is called when the user clicks on the 'textToSpeechButton'.
     * It reads the searched word.
     */
    @FXML
    public void setTextToSpeechButton() {
        String meaning = dictionary.search(searchField.getText());
        if (searchField.getText().isEmpty()) {
            TextToSpeech.soundEnToVi("Please enter a word");
        } else {
            if (!meaning.equals("<h1 style=\"text-align: center;\">No word found</h1>")) {
                TextToSpeech.soundEnToVi(searchField.getText());
            } else {
                TextToSpeech.soundEnToVi("No word found");
            }
        }
    }


    /**
     * Called when the 'Delete Word' button is clicked in the GUI.
     * If no word is selected or the selected word does not exist in the dictionary, it shows an information alert.
     * If a word is selected, and it exists in the dictionary, it shows a warning alert asking for confirmation to delete the word.
     * If the user confirms, it deletes the word from the dictionary and shows a success alert.
     * If the user cancels, it shows an information alert.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setDeleteWordButton(ActionEvent event) {
        if (latestWord.isEmpty()) {
            // No word selected
            alerts.showAlertInfo("Information", "Vui lòng chọn từ cần xóa");

        } else if (dictionary.search(latestWord).equals("<h1 style=\"text-align: center;\">No word found</h1>")) {
            alerts.showAlertInfo("Information", "Từ này không tồn tại");
        } else {
            Alert alertWarning = alerts.alertWarning("Delete", "Bạn chắc chắn muốn xóa từ này?");
            // option != null.
            alertWarning.getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> option = alertWarning.showAndWait();
            if (option.get() == ButtonType.OK) {
                // delete selected word from dictionary
                dictionary.delete(latestWord);
                // succeed
                alerts.showAlertInfo("Information", "Xóa thành công");
            } else {
                alerts.showAlertInfo("Information", "Thay đổi không được công nhận");
            }
        }
    }

    /**
     * This method is called when the 'Edit Word' button is clicked in the GUI.
     * If no word is selected or the selected word does not exist in the dictionary, it shows an information alert.
     * If a word is selected, and it exists in the dictionary, it loads a new scene from the 'EditWord.fxml' file and sets it on a new stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setEditWordButton(ActionEvent event) {
        try {
            UpdateWordController.setEditWord(latestWord);
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/EditWord.fxml")));
            Scene scene = new Scene(root, 779, 413);
            popup.setTitle("Edit Word Meaning");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
            latestWord = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
