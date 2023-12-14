package com.example.demo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class historyController extends MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editDefinition.setVisible(false);
        try {
            super.loadDictionaryData();
            super.loadBookmarkData();
            super.loadHistoryData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<String> entries = historyDictionary.getWords().keySet();
        searchList.addAll(entries);
        resultListView.setItems(searchList);
        editDefinition.setVisible(false); //unable edit
        saveChangeBtn.setVisible(false);
    }


    /** load the history dictionary from the file
     *
     */
    public void setSearchList() {
        super.setSearchList(historyDictionary);
    }

    @FXML
    public void searchTextFieldAction(KeyEvent keyEvent) {
        super.setSearchTextFieldAction(keyEvent, bookmarkDictionary);
//        String searchWord = searchTextField.getText().trim().toLowerCase();
//        if (keyEvent.getCode().equals(KeyCode.ENTER)){
//            if (searchList.isEmpty() || searchWord.equals("")) {
//                setSearchList();
//                notFoundWordAlert();
//            } else if (dictionaryManagement.searcher(historyDictionary, searchWord).isEmpty()) {
//                notFoundWordAlert();
//                setSearchList();
//            }
//            else {
//                resultListView.setItems(dictionaryManagement.searcher(historyDictionary, searchWord));
//            }
//        } else {
//            setSearchList();
//        }
    }

    @FXML
    public void handleClickListView() {
        String chosenWord = resultListView.getSelectionModel().getSelectedItem();
        if (chosenWord == null) {
            return;
        } else {
            String meaning = historyDictionary.translate(chosenWord);
            explainView.getEngine().loadContent(meaning, "text/html");
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
    public void handleClickEditBtn(ActionEvent actionEvent) {
        super.handleClickEditBtn();
    }

    @FXML
    public void handleClickSaveBtn(ActionEvent actionEvent) throws IOException {
        super.handleClickSaveBtn();
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
