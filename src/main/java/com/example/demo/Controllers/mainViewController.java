package com.example.demo.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainViewController implements Initializable {

    public AnchorPane getSearchPane() {
        if (searchPane == null){
            try {
                searchPane = new FXMLLoader(getClass().getResource("/com/example/demo/search.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchPane;
    }

    public AnchorPane getTranslatePane() {
        if (translatePane == null){
            try {
                translatePane = new FXMLLoader(getClass().getResource("/com/example/demo/translate.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return translatePane;
    }

    public AnchorPane getAddWordPane() {
        if (addWordPane == null){
            try {
                addWordPane = new FXMLLoader(getClass().getResource("/com/example/demo/addWord.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addWordPane;
    }

    private void addPane(AnchorPane anchorPane) {
        container.getChildren().clear();
        container.getChildren().add(anchorPane);
    }

    private void setContainer(String path) {
        try {
            AnchorPane container = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            addPane(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //use these when dont want to create new anchorpane each time you switch between one to another
//        searchBtn.setOnAction((ActionEvent event) -> {
//           try {
//              addNode(getSearchPane());
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
//        });
//
//        translateBtn.setOnAction((ActionEvent event) -> {
//            try {
//                addNode(getTranslatePane());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        addBtn.setOnAction((ActionEvent event) -> {
//            try {
//                addNode(getAddWordPane());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        //reload the anchorpane each time you switch
        searchBtn.setOnAction((ActionEvent event) -> {
            setContainer("/com/example/demo/search.fxml");
        });

        translateBtn.setOnAction((ActionEvent event) -> {
            setContainer("/com/example/demo/translate.fxml");
        });
        addBtn.setOnAction((ActionEvent event) -> {
            setContainer("/com/example/demo/addWord.fxml");
        });

        bookmarkBtn.setOnAction((ActionEvent event) -> {
            setContainer("/com/example/demo/bookmark.fxml");
        });

        historyBtn.setOnAction((ActionEvent event) -> {
            setContainer("/com/example/demo/history.fxml");
        });

        exitBtn.setOnAction((ActionEvent event ) -> {
            javafx.application.Platform.exit();
        } );

        setContainer("/com/example/demo/search.fxml");
    }

    @FXML
    public Button searchBtn;
    @FXML
    public Tooltip searchTooltip;
    @FXML
    public Button translateBtn;
    @FXML
    public Tooltip translateTooltip;
    @FXML
    public Button addBtn;
    @FXML
    public Tooltip addTooltip;
    @FXML
    public Button historyBtn;
    @FXML
    public Tooltip historyTooltip;
    @FXML
    public Button bookmarkBtn;
    @FXML
    public Tooltip bookmarkTooltip;
    @FXML
    public AnchorPane container;
    @FXML
    public Button exitBtn;

    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private AnchorPane addWordPane;

}
