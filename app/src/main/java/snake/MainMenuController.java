package snake;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
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
        Main.startGame(stage); // Startgame -> starGame method
    }

    @FXML
    private void settingsGoto(ActionEvent event) { // Press on settings
        Settings settings = Main.getSettings(); // Get settings from Main class
        Dialog<HashMap<String, String>> dialog = new Dialog<>(); // Create dialog
        dialog.setTitle("Settings");
        dialog.setHeaderText("Settings for Snake");
        dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/icon32.png"))));

        ButtonType applyBtn = new ButtonType("Apply", ButtonData.APPLY);

        dialog.getDialogPane().getButtonTypes().addAll(applyBtn, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField width = new TextField(); // Textfields for width and height
        width.setPromptText("20");
        TextField height = new TextField();
        height.setPromptText("20");

        grid.add(new Label("Width [5 - 100]"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height [5 - 100]"), 0, 1);
        grid.add(height, 1, 1);

        CheckBox walls = new CheckBox("Enable Walls"); // Checkboxes
        CheckBox warp = new CheckBox("Enable Wormholes");
        CheckBox bomb = new CheckBox("Enable bombs");
        Rectangle wallTangle = new Rectangle(20, 20);
        wallTangle.setFill(Color.BLACK);
        wallTangle.setStroke(Color.BLACK);
        Rectangle warpTangle = new Rectangle(20, 20);
        warpTangle.setFill(Color.PURPLE);
        warpTangle.setStroke(Color.BLACK);
        Rectangle bombTangle = new Rectangle(20, 20);
        bombTangle.setFill(Color.CHARTREUSE);
        bombTangle.setStroke(Color.BLACK);

        if (settings.isWallsOn()) // Set checkboxes according to settings
            walls.setSelected(true);
        if (settings.isWarpsOn())
            warp.setSelected(true);
        if (settings.isBombsOn())
            bomb.setSelected(true);

        grid.add(walls, 1, 2);
        grid.add(wallTangle, 0, 2);
        grid.add(warp, 1, 3);
        grid.add(warpTangle, 0, 3);
        grid.add(bomb, 1, 4);
        grid.add(bombTangle, 0, 4);

        Slider speed = new Slider(10, 50, settings.getSpeed()); // Slider with current speed from settings

        grid.add(new Label("Fast\nSnake"), 0, 5);
        grid.add(speed, 1, 5);
        grid.add(new Label("Slow\nSnake"), 2, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyBtn) {
                HashMap<String, String> newSettings = new HashMap<String, String>();
                newSettings.put("Width", width.getText());
                newSettings.put("Height", height.getText());
                newSettings.put("Walls", String.valueOf(walls.isSelected()));
                newSettings.put("Warp", String.valueOf(warp.isSelected()));
                newSettings.put("Bomb", String.valueOf(bomb.isSelected()));
                newSettings.put("Speed", String.valueOf(speed.getValue()));
                return newSettings; // Create new settings from dialog box
            }
            return null;
        });
        Optional<HashMap<String, String>> result = dialog.showAndWait();

        result.ifPresent(newSettings -> {
            if (!newSettings.get("Width").isBlank()){ // If new width is given, set a new width in main
                int newWidth = Integer.parseInt(newSettings.get("Width"));
                if(newWidth >= 5 && newWidth <= 100){
                    Main.width = newWidth;
                } else {
                    Main.width = 20;
                }
            }
            if (!newSettings.get("Height").isBlank()){ // If new height is given, set a new height in main
                int newHeight = Integer.parseInt(newSettings.get("Height"));
                if(newHeight >= 5 && newHeight <= 100){
                    Main.height = newHeight;
                } else {
                    Main.height = 20;
                }
            }
            if (newSettings.get("Walls").equals("true")) { // Set new setting according to checkbox
                settings.setWallsOn(true);
            } else if (newSettings.get("Walls").equals("false")) {
                settings.setWallsOn(false);
            }
            if (newSettings.get("Warp").equals("true")) { // Set new setting according to checkbox
                settings.setWarpsOn(true);
            } else if (newSettings.get("Warp").equals("false")) {
                settings.setWarpsOn(false);
            }
            if (newSettings.get("Bomb").equals("true")) { // Set new setting according to checkbox
                settings.setBombsOn(true);
            } else if (newSettings.get("Bomb").equals("false")) {
                settings.setBombsOn(false);
            }
            settings.setSpeed(Double.valueOf(newSettings.get("Speed"))); // Set new speed according to slider
            Main.setSettings(settings);
        });
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main.exit(stage); // Exit method
    }

    @FXML
    private void highScorePrompt(ActionEvent event) {
        String highScoreText;
        Alert alert = new Alert(AlertType.INFORMATION);
        Object[] hsArr = Main.loadHighScore(); // Get highscore from file
        alert.setTitle("Highscore");
        switch ((int) hsArr[0]) {
            case -1: // On error string
                highScoreText = "No highscore set";
                break;
            default: // Print highscore
                highScoreText = "The HighScore is " + hsArr[0] + " set by "
                        + hsArr[1];
        }
        alert.setContentText(highScoreText);
        alert.setHeaderText(null);
        ImageView graphic = new ImageView(new Image(getClass().getResourceAsStream("/icons/icon32.png")));
        alert.setGraphic(graphic);
        ButtonType response = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (response == ButtonType.OK) {
            alert.close();
        }
        alert.close();
    }
}