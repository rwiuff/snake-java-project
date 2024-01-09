package snake;

import javafx.scene.Node;
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
        Main.startGame(stage);
    }

    @FXML
    private void settingsGoto(ActionEvent event) {
        Dialog<HashMap<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Settings");
        dialog.setHeaderText("Settings for Snake");
        dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/icon32.png"))));

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

        CheckBox walls = new CheckBox("Enable Walls");
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

        if (Main.wallsOn)
            walls.setSelected(true);
        if (Main.warpsOn)
            warp.setSelected(true);
        if (Main.bombsOn)
            bomb.setSelected(true);

        grid.add(walls, 1, 2);
        grid.add(wallTangle, 0, 2);
        grid.add(warp, 1, 3);
        grid.add(warpTangle, 0, 3);
        grid.add(bomb, 1, 4);
        grid.add(bombTangle, 0, 4);

        Slider speed = new Slider(10, 50, 20);

        grid.add(new Label("Fast\nSnake"), 0, 5);
        grid.add(speed, 1, 5);
        grid.add(new Label("Slow\nSnake"), 2, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyBtn) {
                HashMap<String, String> settings = new HashMap<String, String>();
                settings.put("Width", width.getText());
                settings.put("Height", height.getText());
                settings.put("Walls", String.valueOf(walls.isSelected()));
                settings.put("Warp", String.valueOf(warp.isSelected()));
                settings.put("Bomb", String.valueOf(bomb.isSelected()));
                settings.put("Speed", String.valueOf(speed.getValue()));
                return settings;
            }
            return null;
        });
        Optional<HashMap<String, String>> result = dialog.showAndWait();

        result.ifPresent(settings -> {
            if (!settings.get("Width").isBlank())
                Main.width = Integer.parseInt(settings.get("Width"));
            if (!settings.get("Height").isBlank())
                Main.height = Integer.parseInt(settings.get("Height"));
            if (settings.get("Walls").equals("true")) {
                Main.wallsOn = true;
            } else if (settings.get("Walls").equals("false")) {
                Main.wallsOn = false;
            }
            if (settings.get("Warp").equals("true")) {
                Main.warpsOn = true;
            } else if (settings.get("Warp").equals("false")) {
                Main.warpsOn = false;
            }
            if (settings.get("Bomb").equals("true")) {
                Main.bombsOn = true;
            } else if (settings.get("Bomb").equals("false")) {
                Main.bombsOn = false;
            }
            Main.speed = Double.valueOf(settings.get("Speed"));
        });
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main.exit(stage);
    }

}