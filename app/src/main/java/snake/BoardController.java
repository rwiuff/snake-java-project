package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Set;
import java.awt.Point;

public class BoardController {
    @FXML
    private BorderPane borderPane;
    private int width;
    private int height;
    private Board board;
    private int fieldsize;
    private HashMap<String, Point> changesMap;
    private Timeline realtime;
    private int tick = 1;
    private int prevDir = 3;
    private int[] queue = { 3, 3 };

    public void run(Scene scene) {
        this.board = new Board(width, height);
        this.realtime = new Timeline(
                new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int direction = queue[0];
                        board.getSnake().getHead().setDir(direction);
                        if (tick++ % 6 == 0) {
                            changesMap = board.update();
                            reDrawBoard(scene, changesMap);
                            tick = 1;
                            queue[0] = queue[1]; // direction input is used
                            prevDir = direction;

                        }

                    }
                }));
        this.realtime.setCycleCount(Timeline.INDEFINITE);
        this.realtime.play();
        drawBoard();

        scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                switch (event.getCharacter()) {
                    case "w":
                        if (queue[1] % 2 != 0) {
                            queue[0] = queue[1];
                            queue[1] = 0;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 0;
                            }
                        }

                        break;
                    case "d":
                        if (queue[1] % 2 != 1) {
                            queue[0] = queue[1];
                            queue[1] = 1;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 1;
                            }
                        }
                        break;
                    case "s":
                        if (queue[1] % 2 != 0) {
                            queue[0] = queue[1];
                            queue[1] = 2;
                            if (queue[0]==prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 2;
                            }

                        }
                        break;
                    case "a":
                        if (queue[1] % 2 != 1) {
                            queue[0] = queue[1];
                            queue[1] = 3;
                            if (queue[0]==prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 3;
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid keypress");
                        break;
                }

            }
        });
    }

    protected void reDrawBoard(Scene scene, HashMap<String, Point> updateFields) {
        Set<String> keys = updateFields.keySet();
        for (String key : keys) {
            Point updateObject = updateFields.get(key);
            String lookup = "#" + (int) updateObject.getY() + ";" + (int) updateObject.getX();
            Rectangle rectangle = (Rectangle) scene.lookup(lookup);
            if (key.equals("Apple")) {
                rectangle.setFill(Color.RED);
            } else if (key.equals("Head")) {
                rectangle.setFill(Color.BLACK);
            } else if (key.equals("Empty")) {
                rectangle.setFill(Color.OLIVE);
            } else if (key.equals("OldHead")) {
                rectangle.setFill(Color.GRAY);
            } else if (key.equals("GhostTail")) {
                rectangle.setFill(Color.GRAY);
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
                    rectangle.setFill(Color.OLIVE);
                }
                rectangle.setStroke(Color.WHEAT);
                rectangle.setId(i + ";" + j);
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
    }

    public void retry() {
        this.board = new Board(this.board.getBoard().length, this.board.getBoard()[0].length);
        queue[0] = 3;
        queue[1] = 3;
        this.tick = 1;
        this.realtime.play();

    }

}