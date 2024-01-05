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
    // @FXML
    // private GridPane gridPane;
    @FXML
    private BorderPane borderPane;

    private int width;
    private int height;
    private Board board;
    private HashMap<String, Point> changesMap;
    private Timeline realtime;
    private int direction=3;
    private int tick = 1;
    private int prevDir;
    public void run(Scene scene) {
        this.board = new Board(width, height);
        this.realtime = new Timeline(
                new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int tempDirection=direction;
                        board.getSnake().getHead().setDir(tempDirection);
                        if (tick++ % 150 == 0) {
                            changesMap = board.update();
                            reDrawBoard(scene, changesMap);
                            tick = 1;
                            prevDir=tempDirection;
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
                        if (prevDir%2!=0) {
                            direction = 0;}
                        break;
                    case "d":
                        if (prevDir%2!=1) {
                            direction = 1;}
                        break;
                    case "s":
                        if (prevDir%2!=0) {
                        direction = 2;}
                        break;
                    case "a":
                        if (prevDir%2!=1) {
                            direction = 3;}
                        break;
                    default:
                        System.out.println("Invalid keypress");
                        direction = 4;
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
                rectangle.setWidth(20); // change size of squares here
                rectangle.setHeight(20); // change size of squares here
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
        this.board=new Board(this.board.getBoard().length,this.board.getBoard()[0].length);
        this.direction=3;
        this.prevDir=3;
        this.tick=1;
        this.realtime.play();
        
    }

}