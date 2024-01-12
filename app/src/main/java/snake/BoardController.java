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
import java.util.Set;
import java.awt.Point;

public class BoardController {
    @FXML
    private BorderPane borderPane;

    private Color fieldColor = Color.SILVER;
    private int width;
    private int height;
    private Board board;
    private Set<Point> changesMap;

    private int fieldsize;

    public void run(Scene scene) {
        this.board = new Board(height, width); // Construct board
        drawBoard(); // Draw objects on board
        scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() { // Eventhandler listens to keyboard
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
                        direction = 5;
                        break;
                }
                board.getSnake().getHead().setDir(direction); // Snake gets direction
                changesMap = board.update(); // Board updates game logic and returns a set of points that changes during
                                             // move
                reDrawBoard(scene, changesMap); // Update the view to include changes
            }
        });
    }

    protected void reDrawBoard(Scene scene, Set<Point> changesMap) {
        for (Point point : changesMap) { // Iterate over changes
            int y = (int) point.getY();
            int x = (int) point.getX();
            String lookup = "#" + y + ";" + x;
            Rectangle rectangle = (Rectangle) scene.lookup(lookup); // Find square
            try {
                rectangle.setFill(board.board[x][y].getColor()); // Recolour square
            } catch (NullPointerException e) {
                rectangle.setFill(fieldColor); // Default to fieldcolour (silver)
            }
        }
    }

    public void setDimensions(int width, int height, int fieldSize) {
        this.width = width;
        this.height = height;
        this.fieldsize = fieldSize;
    }

    public void drawBoard() {
        GridPane gridPane = new GridPane();
        Space[][] spaces = board.getBoard();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(fieldsize); // change size of squares here
                rectangle.setHeight(fieldsize); // change size of squares here
                try {
                    rectangle.setFill(spaces[j][i].getColor());
                } catch (NullPointerException e) {
                    rectangle.setFill(this.fieldColor);
                }
                rectangle.setStroke(this.fieldColor);
                rectangle.setId(i + ";" + j);
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
    }

}