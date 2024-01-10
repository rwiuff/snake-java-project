package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Set;

import java.awt.Point;
import java.util.Random;

public class BoardController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button startGameBtn;
    @FXML
    private VBox instructionOverlay;
    @FXML
    private VBox pauseOverlay;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button menuBtn;
    @FXML
    private Label scoreLabel;

    private Color fieldColor = Color.SILVER;

    private int width;
    private int height;
    private boolean wallsOn;
    private boolean warpsOn;
    private Board board;
    private Set<Point> changesMap;
    private Timeline realtime;
    private int tick = 1;
    private int fieldsize;
    private Scene scene;
    private int prevDir = 3;
    private int[] queue = { 3, 3 };
    private double speed;
    private boolean bombsOn;
    private Random rng = new Random();

    @FXML
    private void startGame(ActionEvent event) {
        instructionOverlay.setVisible(false);
        pauseOverlay.setVisible(false);
        borderPane.setVisible(true);
        run(scene);
    }

    public void setSettings(Settings settings) {
        this.speed = settings.getSpeed();
        this.wallsOn = settings.isWallsOn();
        this.warpsOn = settings.isWarpsOn();
        this.bombsOn = settings.isBombsOn();
    }

    public void setup(Scene scene) {
        this.scene = scene;
        this.board = new Board(height, width, wallsOn, warpsOn);
        drawBoard();
        pauseOverlay.setVisible(false);
        borderPane.setVisible(false);
        pauseBtn.setFocusTraversable(false);
        menuBtn.setFocusTraversable(false);
    }

    @FXML
    private void pause(ActionEvent event) {
        pauseOverlay.setVisible(true);
        borderPane.setVisible(false);
        realtime.pause();
    }

    @FXML
    private void continueGame(ActionEvent event) {
        pauseOverlay.setVisible(false);
        borderPane.setVisible(true);
        realtime.play();
    }

    @FXML
    private void menu(ActionEvent event) {
        realtime.pause();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to exit your game");
        alert.setContentText("Progress will be lost");
        alert.setTitle("Go to main menu");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Main.mainMenu(stage);
        } else {
            realtime.play();
        }
    }

    public void run(Scene scene) {
        if (!this.bombsOn) {
            this.realtime = new Timeline(
                    new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>() {
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
                                board.clearChangesMap();
                            }

                        }
                    }));
            this.realtime.setCycleCount(Timeline.INDEFINITE);
            this.realtime.play();
        } else {
            this.realtime = new Timeline(
                    new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>() {
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
                                board.clearChangesMap();
                                int boardSize = board.getBoardSize();
                                if (rng.nextInt(boardSize) < boardSize
                                        / (board.getSnake().getLength() / Math.min(board.getHeight(), board.getWidth())
                                                + 2)) {
                                    board.placeBomb(Math.max(board.getHeight(), board.getWidth())); // expiration time
                                                                                                    // of bomb
                                }

                            }

                        }
                    }));
            this.realtime.setCycleCount(Timeline.INDEFINITE);
            this.realtime.play();
        }
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:

                    case W:
                        if (queue[1] % 2 != 0) {
                            queue[0] = queue[1];
                            queue[1] = 0;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 0;
                            }
                        }

                        break;
                    case RIGHT:

                    case D:
                        if (queue[1] % 2 != 1) {
                            queue[0] = queue[1];
                            queue[1] = 1;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 1;
                            }
                        }
                        break;
                    case DOWN:

                    case S:
                        if (queue[1] % 2 != 0) {
                            queue[0] = queue[1];
                            queue[1] = 2;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 2;
                            }

                        }
                        break;
                    case LEFT:

                    case A:
                        if (queue[1] % 2 != 1) {
                            queue[0] = queue[1];
                            queue[1] = 3;
                            if (queue[0] == prevDir) {
                                queue[0] = queue[1];
                                queue[1] = 3;
                            }
                        }
                        break;
                    case SPACE:
                        pause(null);
                        break;
                    // case F11:
                    // Stage stage = (Stage) scene.getWindow();
                    // stage.setFullScreen(!stage.isFullScreen());
                    // break;
                    default:
                        System.out.println("Invalid keypress");
                        break;
                }

            }
        });
        // Stage stage = (Stage) scene.getWindow();
        // stage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
        // @Override
        // public void changed(ObservableValue<? extends Boolean> observable, Boolean
        // oldValue,
        // Boolean newValue) {
        // Main.resize(stage);
        // }
        // });
    }

    protected void reDrawBoard(Scene scene, Set<Point> changesMap) {
        for (Point point : changesMap) {
            int y = (int) point.getY();
            int x = (int) point.getX();
            String lookup = "#" + y + ";" + x;
            Rectangle rectangle = (Rectangle) scene.lookup(lookup);
            try {
                rectangle.setFill(board.board[x][y].getColor());
            } catch (NullPointerException e) {
                rectangle.setFill(fieldColor);
            }
        }
        scoreLabel.setText("Score: " + ((board.getSnake().getLength()) - 2) * 100);
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
                    rectangle.setFill(fieldColor);
                }
                rectangle.setStroke(fieldColor);
                rectangle.setId(i + ";" + j);
                gridPane.add(rectangle, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
    }

    @FXML
    public void retry(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to restart your game");
        alert.setContentText("Progress will be lost");
        alert.setTitle("Restart game");
        if (alert.showAndWait().get() == ButtonType.OK) {
            pauseOverlay.setVisible(false);
            borderPane.setVisible(true);
            retry();
        }
    }

    public void retry() {
        this.board = new Board(this.board.getBoard().length, this.board.getBoard()[0].length, wallsOn, warpsOn);
        drawBoard();
        queue[0] = 3;
        queue[1] = 3;
        this.tick = 1;
        this.realtime.play();

    }

    public void gameOver(int score) {
        realtime.stop();
        Stage stage = (Stage) scene.getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        String headerText = board.getSnake().getLength() == width * height ? "You won!\n" : "Game Over\n";
        headerText = headerText + "Your score is: " + score;
        alert.setHeaderText(headerText);
        alert.setContentText("What now?");

        ButtonType retryButton = new ButtonType("Retry");
        ButtonType mainButton = new ButtonType("Main Menu");
        ButtonType exitButton = new ButtonType("Exit game");

        alert.getButtonTypes().setAll(retryButton, mainButton, exitButton);

        alert.setOnCloseRequest(e -> {
            ButtonType result = alert.getResult();
            if (result == retryButton) {
                retry();
            } else if (result == mainButton) {
                Main.mainMenu(stage);
            } else if (result == exitButton) {
                Main.exit(stage);
            }
        });

        alert.show();
    }

}