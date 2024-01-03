package snake;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardController implements Initializable {
    @FXML
    private GridPane gridPane;

    private Rectangle rectangle;

    private int width;
    private int height;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridPane = new GridPane();
        rectangle = new Rectangle();
        // r.setX(50);
        // r.setY(50);
        rectangle.setWidth(10);
        rectangle.setHeight(10);
        // r.setArcWidth(20);
        // r.setArcHeight(20);
        rectangle.setFill(Color.OLIVE);
        rectangle.setStroke(Color.WHEAT);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
}