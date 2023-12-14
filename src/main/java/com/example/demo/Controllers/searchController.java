package com.example.demo.Controllers;

import com.example.demo.Cli.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

public class searchController extends MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.loadDictionaryData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<String> entries = dictionary.getWords().keySet();
        searchList.addAll(entries);
        resultListView.setItems(searchList);
        editDefinition.setVisible(false); //unable edit
        saveChangeBtn.setVisible(false);
    }

    public void setSearchList() {
        super.setSearchList(dictionary);
    }


    /** show the match result when search for the word.
     *
     */
    @FXML
    public void searchTextFieldAction(KeyEvent keyEvent) {
        super.setSearchTextFieldAction(keyEvent, dictionary);
//        String searchWord = searchTextField.getText().trim().toLowerCase();
//        if (keyEvent.getCode().equals(KeyCode.ENTER)){
//            if (searchList.isEmpty() || searchWord.equals("")) {
//                setSearchList();
//                notFoundWordAlert();
//            } else if (dictionaryManagement.searcher(dictionary, searchWord).isEmpty()) {
//                notFoundWordAlert();
//                setSearchList();
//            }
//            else {
//                resultListView.setItems(dictionaryManagement.searcher(dictionary, searchWord));
//            }
//        } else {
//            setSearchList();
//        }
    }

    @FXML
    public void handleClickListView() throws IOException {
        String chosenWord = resultListView.getSelectionModel().getSelectedItem();
        if (chosenWord == null) {
            return;
        } else {
            String meaning = dictionary.translate(chosenWord);
            explainView.getEngine().loadContent(meaning, "text/html");
            Word word = new Word(chosenWord, meaning);
            historyDictionary.insert(word);
            super.updateHistoryDictionary();
        }
    }



    @FXML
    public void handleClickUKSoundBtn(ActionEvent actionEvent) throws UnsupportedEncodingException {
        super.handleClickUKSoundBtn();
    }

    @FXML
    public void handleClickUSSoundBtn(ActionEvent actionEvent) throws UnsupportedEncodingException{
        super.handleClickUSSoundBtn();
    }

    @FXML
    public void handleClickSaveBtn(ActionEvent actionEvent) throws IOException {
        super.handleClickSaveBtn();
    }

    @FXML
    public void handleClickEditBtn(ActionEvent actionEvent) {
        super.handleClickEditBtn();
    }

    @FXML
    public void handleClickRemoveBtn(ActionEvent actionEvent) throws IOException {
        super.handleClickRemoveBtn();
    }

    @FXML
    public void handleClickBookmarkBtn(ActionEvent actionEvent) throws IOException {
        super.handleClickBookmarkBtn();
    }

}
