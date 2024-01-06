package snake;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenu{
    @FXML
    Button startBtn;

    @FXML
    Button settingsBtn;

    @FXML
    Button exitBtn;

    @FXML
    private void startGame(ActionEvent event){
        System.out.println("Start");
    }

    @FXML
    private void settingsGoto(ActionEvent event){
        System.out.println("Settings");
    }

    @FXML
    private void exitProgram(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main.exit(stage);
    }

}