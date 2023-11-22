package com.example.demo.Controllers;

import com.example.demo.Cli.Word;
import com.example.demo.Cli.Dictionary;
import com.example.demo.Cli.DictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Set;

public class historyController implements Initializable {
    private static final String DATA_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\Data\\E_V.txt";
    private static final String Bookmark_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\Data\\bookmark.txt";
    private static final String History_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\Data\\history.txt";
    private final ObservableList<String> searchList = FXCollections.observableArrayList();
    private final ObservableList<String> bookmarkList = FXCollections.observableArrayList();
    private final Dictionary bookmarkDictionary = new Dictionary();
    private final Dictionary historyDictionary = new Dictionary();
    private final Dictionary dictionary = new Dictionary();
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    protected boolean isEdit = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editDefinition.setVisible(false);
        try {
            DictionaryManagement.loadDataFromFile(dictionary, DATA_PATH);
            DictionaryManagement.loadDataFromFile(bookmarkDictionary, Bookmark_PATH);
            DictionaryManagement.loadDataFromFile(historyDictionary, History_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<String> entries = historyDictionary.getWords().keySet();
        searchList.addAll(entries);
        resultListView.setItems(searchList);
        editDefinition.setVisible(false); //unable edit
        saveChangeBtn.setVisible(false);
    }

    @FXML
    public void searchHistoryTextFieldAction(KeyEvent keyEvent) {
        String searchWord = searchTextField.getText().trim().toLowerCase();
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            if (searchList.isEmpty() || searchWord.equals("")) {
                setSearchList();
                notFoundWordAlert();
            } else if (dictionaryManagement.searcher(historyDictionary, searchWord).isEmpty()) {
                notFoundWordAlert();
                setSearchList();
            }
            else {
                resultListView.setItems(dictionaryManagement.searcher(historyDictionary, searchWord));
            }
        } else {
            setSearchList();
        }
    }
    /** load the history dictionary from the file
     *
     */
    public void setSearchList() {
        searchList.clear();
        Set<String> entries = historyDictionary.getWords().keySet();
        searchList.addAll(entries);
        resultListView.setItems(searchList);
    }

    /** show the alert when cannot find the word you need.
     *
     */
    public void notFoundWordAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Không tìm thấy từ bạn tìm kiếm!");
        alert.showAndWait();
    }

    @FXML
    public void handleClickListView(MouseEvent mouseEvent) {
        String chosenWord = resultListView.getSelectionModel().getSelectedItem();
        if (chosenWord == null) {
            return;
        } else {
            String meaning = historyDictionary.translate(chosenWord);
            explainView.getEngine().loadContent(meaning, "text/html");
        }
    }

    public void notChoseWordAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Không có từ nào được chọn!");
        alert.showAndWait();
    }


    @FXML
    public void handleClickUKSoundBtn(ActionEvent actionEvent) throws UnsupportedEncodingException {
        String word = resultListView.getSelectionModel().getSelectedItem();
        if (word == null) {
            notChoseWordAlert();
        } else {
            String URL = "https://translate.google.com.vn/translate_tts?ie=UTF-8&q=" + URLEncoder.encode(word, StandardCharsets.UTF_8) + "&tl=en-uk&client=tw-ob";
            Media sound = new Media(URL);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    @FXML
    public void handleClickUSSoundBtn(ActionEvent actionEvent) throws UnsupportedEncodingException{
        String word = resultListView.getSelectionModel().getSelectedItem();
        if (word == null) {
            notChoseWordAlert();
        } else {
            String URL = "https://translate.google.com.vn/translate_tts?ie=UTF-8&q=" + URLEncoder.encode(word, StandardCharsets.UTF_8) + "&tl=en-us&client=tw-ob";
            Media sound = new Media(URL);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    @FXML
    public void handleClickEditBtn(ActionEvent actionEvent) {
        String word = resultListView.getSelectionModel().getSelectedItem();
        if (word == null) {
            notChoseWordAlert();
            return;
        }
        if (isEdit) {
            isEdit = false;
            editDefinition.setVisible(false);
            saveChangeBtn.setVisible(false);
            return;
        }
        isEdit = true;
        saveChangeBtn.setVisible(true);
        editDefinition.setVisible(true);
        String meaning = dictionary.translate(word);
        editDefinition.setHtmlText(meaning);
    }

    @FXML
    public void handleClickSaveBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Sửa từ thành công!");
        alert.showAndWait();
        saveChangeBtn.setVisible(false);
        editDefinition.setVisible(false);
        isEdit = false;
        String newMeaning = editDefinition.getHtmlText().replace(" dir=\"ltr\"", "");
        String word = resultListView.getSelectionModel().getSelectedItem();
        updateWordToFile(dictionary, word, newMeaning, DATA_PATH);
        updateWordToFile(bookmarkDictionary, word, newMeaning, Bookmark_PATH);
        updateWordToFile(historyDictionary, word, newMeaning, History_PATH);
        explainView.getEngine().loadContent(newMeaning, "text/html");
    }

    public void updateWordToFile(Dictionary dictionary, String word, String newMeaning, String path) throws IOException {
        dictionary.getWords().replace(word, newMeaning);
        DictionaryManagement.updateWord(dictionary, path); //to rewrite the dictionary
    }

    @FXML
    public void handleClickRemoveBtn(ActionEvent actionEvent) {
        String word = resultListView.getSelectionModel().getSelectedItem();
        if (word == null) {
            notChoseWordAlert();
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setContentText("Bạn có muốn xóa từ này không");
                alert.showAndWait();
                if (alert.getResult() == javafx.scene.control.ButtonType.OK) {
                    dictionary.removeWord(word);
                    bookmarkDictionary.removeWord(word);
                    resultListView.getItems().remove(word);
                    DictionaryManagement.updateWord(dictionary, DATA_PATH);
                    DictionaryManagement.updateWord(bookmarkDictionary, Bookmark_PATH);
                    DictionaryManagement.updateWord(historyDictionary, History_PATH);
                    explainView.getEngine().loadContent("");
                } else {
                    alert.close();
                }
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void handleClickBookmarkBtn(ActionEvent actionEvent) throws IOException {
        String word = resultListView.getSelectionModel().getSelectedItem();
        if (word == null) {
            notChoseWordAlert();
        } else {
            if (bookmarkDictionary.getWords().containsKey(word)) {
                removeBookmark(word);
            } else {
                addBookmark(word);
            }
        }
    }

    protected void addBookmark(String word) throws IOException {
        bookmarkBtn.setVisible(true);
        notBookmarkBtn.setVisible(false);
        String meaning = dictionary.translate(word);
        Word newWord = new Word(word, meaning);
        bookmarkDictionary.insert(newWord);
        DictionaryManagement.updateWord(bookmarkDictionary, Bookmark_PATH);
    }

    protected void removeBookmark(String word) throws IOException {
        bookmarkBtn.setVisible(false);
        notBookmarkBtn.setVisible(true);
        bookmarkDictionary.removeWord(word);
        DictionaryManagement.updateWord(bookmarkDictionary, Bookmark_PATH);
        setSearchList();
        explainView.getEngine().loadContent("");
    }

    @FXML
    public Button saveChangeBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public Button UKSoundBtn;
    @FXML
    public Button USSoundBtn;
    @FXML
    public Button bookmarkBtn;
    @FXML
    public Button notBookmarkBtn;
    @FXML
    public WebView explainView;
    @FXML
    public HTMLEditor editDefinition;
    @FXML
    public TextField searchTextField;
    @FXML
    public ListView<String> resultListView;

}
