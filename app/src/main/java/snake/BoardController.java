package snake;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardController {
    // @FXML
    // private GridPane gridPane;
    @FXML
    private BorderPane borderPane;

    private int width;
    private int height;

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setGrid() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(10);
                rectangle.setHeight(10);
                rectangle.setFill(Color.OLIVE);
                rectangle.setStroke(Color.WHEAT);
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
    }
}