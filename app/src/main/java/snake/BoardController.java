package snake;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
    private Board board;
    
    public void run(Scene scene) {
        this.board = new Board(width, height);
        drawBoard();
        scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int direction;
                switch (event.getCharacter()) {
                    case "w":
                        direction = 0;
                        break;
                    case "d":
                        direction = 1;
                        break;
                    case "s":
                        direction = 2;
                        break;
                    case "a":
                        direction = 3;
                        break;
                    default:
                        System.out.println("Invalid keypress");
                        direction = 4;
                        break;
                }
                board.getSnake().getHead().setDir(direction);
                board.update();
                drawBoard();
            }
        });
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void drawBoard() {
        GridPane gridPane = new GridPane();
        Space[][] spaces = board.getBoard();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(20); //change size of squares here
                rectangle.setHeight(20); //change size of squares here
                try {
                    rectangle.setFill(spaces[j][i].getColor());
                } catch (NullPointerException e) {
                    rectangle.setFill(Color.OLIVE);
                }
                rectangle.setStroke(Color.WHEAT);
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
    }
    

}