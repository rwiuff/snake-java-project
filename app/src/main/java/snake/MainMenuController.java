package snake;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;

public class MainMenuController {
    @FXML
    Button startBtn;

    @FXML
    Button settingsBtn;

    @FXML
    Button exitBtn;

    @FXML
    private void startGame(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main.startGame(stage);
    }

    @FXML
    private void settingsGoto(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.setHeaderText("Settings for Snake");

        ButtonType applyBtn = new ButtonType("Apply", ButtonData.APPLY);

        dialog.getDialogPane().getButtonTypes().addAll(applyBtn, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField width = new TextField();
        width.setPromptText("20");
        TextField height = new TextField();
        height.setPromptText("20");

        grid.add(new Label("Width"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height"), 0, 1);
        grid.add(height, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyBtn) {
                return new Pair<String, String>(width.getText(), height.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(dimensions -> {
            Main.width = Integer.parseInt(dimensions.getKey());
            Main.height = Integer.parseInt(dimensions.getValue());
        });
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main.exit(stage);
    }

}