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
import javafx.scene.control.TextInputDialog;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BoardController {
    @FXML
    private BorderPane borderPane; // Pane with the game board
    @FXML
    private Button startGameBtn;
    @FXML
    private VBox instructionOverlay; // Pane with welcome screen and instructions
    @FXML
    private VBox pauseOverlay; // Pane with pause instructions and menu buttons
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
    private Set<Point> changeSet;
    private Timeline realtime;

    private int fieldsize;
    private Scene scene;
    private int prevDir = 3;
    private int[] queue = { 3, 3 };
    private double speed;
    private boolean bombsOn;
    private Random rng = new Random();

    @FXML
    private void startGame(ActionEvent event) { // On pressing 'Start game' switch between panes in the scene
        instructionOverlay.setVisible(false);
        pauseOverlay.setVisible(false);
        borderPane.setVisible(true);
        run(scene); // Run the game
    }

    public void setSettings(Settings settings) { // Method for overwriting settings fields
        this.speed = settings.getSpeed();
        this.wallsOn = settings.isWallsOn();
        this.warpsOn = settings.isWarpsOn();
        this.bombsOn = settings.isBombsOn();
    }

    public void setup(Scene scene) { // Construct Board, draw the view and make, everything but the instruction pane
                                     // invisible
        this.scene = scene;
        this.board = new Board(height, width, wallsOn, warpsOn);
        drawBoard();
        pauseOverlay.setVisible(false);
        borderPane.setVisible(false);
        pauseBtn.setFocusTraversable(false);
        menuBtn.setFocusTraversable(false);
    }

    @FXML
    private void pause(ActionEvent event) { // Chane visibility for pause pane and board pane and pause timeline
        pauseOverlay.setVisible(true);
        borderPane.setVisible(false);
        realtime.pause();
    }

    @FXML
    private void continueGame(ActionEvent event) { // Change visibility for pause pane and board pane and and continue
                                                   // timeline
        pauseOverlay.setVisible(false);
        borderPane.setVisible(true);
        realtime.play();
    }

    @FXML
    private void menu(ActionEvent event) { // Pause game and create dialog prompting exit to main menu
        realtime.pause();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to exit your game");
        alert.setContentText("Progress will be lost");
        alert.setTitle("Go to main menu");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Main.mainMenu(stage); // By pressing 'OK', go to main menu
        } else {
            realtime.play(); // By pressing 'Cancel' resume timeline
        }
    }

    public void run(Scene scene) {
        if (!this.bombsOn) { // If bombs are disabled
            this.realtime = new Timeline( // Create timeline
                    new KeyFrame(Duration.millis(speed * 5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            int direction = queue[0];
                            board.getSnake().getHead().setDir(direction); // Set direction on every frame
                            changeSet = board.update(); // Update game logic and get set of changes as points
                            reDrawBoard(scene, changeSet); // Update board view with changed fields
                            queue[0] = queue[1]; // direction input is used
                            prevDir = direction;
                            board.clearChangeSet(); // Destroy changes
                        }
                    }));
            this.realtime.setCycleCount(Timeline.INDEFINITE);
            this.realtime.play();
        } else { // If bombs are enabled
            this.realtime = new Timeline(
                    new KeyFrame(Duration.millis(speed * 5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            int direction = queue[0];
                            board.getSnake().getHead().setDir(direction); // Set direction on every frame
                            changeSet = board.update(); // Update game logic and get set of changes as points
                            reDrawBoard(scene, changeSet); // Update board view with changed fields
                            queue[0] = queue[1]; // direction input is used
                            prevDir = direction;
                            board.clearChangeSet(); // Destroy changes
                            int boardSize = board.getBoardSize();
                            if (rng.nextInt(boardSize) < boardSize
                                    / (board.getSnake().getLength() / Math.min(board.getHeight(), board.getWidth())
                                            + 2)) {
                                board.placeBomb(Math.max(board.getHeight(), board.getWidth())); // expiration time
                                                                                                // of bomb
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
                    default:
                        break;
                }

            }
        });
    }

    protected void reDrawBoard(Scene scene, Set<Point> changesMap) {
        for (Point point : changesMap) { // Iterate over points of fields to be updated
            int y = (int) point.getY();
            int x = (int) point.getX();
            String lookup = "#" + y + ";" + x;
            Rectangle rectangle = (Rectangle) scene.lookup(lookup); // Find square
            try {
                rectangle.setFill(board.board[x][y].getColor()); // Recoulour square
            } catch (NullPointerException e) {
                rectangle.setFill(fieldColor); // Default square color
            }
        }
        scoreLabel.setText("Score: " + ((board.getSnake().getLength()) - 2) * 100); // Update score label
    }

    public void setDimensions(int width, int height, int fieldSize) {
        this.width = width;
        this.height = height;
        this.fieldsize = fieldSize;
    }

    public void drawBoard() {
        GridPane gridPane = new GridPane(); // Construct gridpane
        Space[][] spaces = board.getBoard();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(fieldsize); // change size of squares here
                rectangle.setHeight(fieldsize); // change size of squares here
                try {
                    rectangle.setFill(spaces[j][i].getColor()); // Get colour of object on coordinate
                } catch (NullPointerException e) {
                    rectangle.setFill(fieldColor); // Default field colour
                }
                rectangle.setStroke(fieldColor);
                rectangle.setId(i + ";" + j); // Create ID for every square based on coordinate
                gridPane.add(rectangle, i, j); // Add to gridpane
            }
        }
        gridPane.setAlignment(Pos.CENTER); // Center squares on gridpane
        borderPane.setCenter(gridPane); // Center gridpane on board
    }

    @FXML
    public void retry(ActionEvent event) { // Dialog that restarts the game
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

    public void retry() { // Method that constructs a new game and starts the timeline
        this.board = new Board(this.board.getBoard().length, this.board.getBoard()[0].length, wallsOn, warpsOn);
        drawBoard();
        queue[0] = 3;
        queue[1] = 3;
        this.realtime.play();
    }

    public void gameOver(int score) { // Dialog boxes when the game is over
        realtime.stop(); // Stop timeline
        Stage stage = (Stage) scene.getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        String headerText = board.getSnake().getLength() == width * height ? "You won!\n" : "Game Over\n";
        headerText = headerText + "Your score is: " + score; // Display score
        alert.setHeaderText(headerText);
        alert.setContentText("What now?");

        ButtonType retryButton = new ButtonType("Retry");
        ButtonType mainButton = new ButtonType("Main Menu");
        ButtonType exitButton = new ButtonType("Exit game");

        alert.getButtonTypes().setAll(retryButton, mainButton, exitButton);

        alert.setOnCloseRequest(e -> { // When dialog closes do one of the following
            ButtonType result = alert.getResult();
            if (result == retryButton) {
                retry(); // If retry is pressed restart and reinstantiate game
            } else if (result == mainButton) {
                Main.mainMenu(stage); // If main menu is pressed change to main menu
            } else if (result == exitButton) {
                Main.exit(stage); // If exit is pressed launch exit dialog
            }
        });

        alert.show();
        if (score > (int) Main.loadHighScore()[0]) { // If highscore is beat, create dialog
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New highscore");
            dialog.setHeaderText("Congratulations! You beat the highscore");
            dialog.setContentText("Please enter your name:");
            dialog.setOnCloseRequest(e -> {
                String name = dialog.getResult();
                try {
                    if (!name.isBlank()) // If a name has been entered
                        saveHighScore(score, name); // Save the new highscore with name
                    else if (name.isBlank()) // If no name is given
                        saveHighScore(score, "Anonymous"); // Save the new highscore with no name
                } catch (NullPointerException e1) { // If cancel button is hit
                    saveHighScore(score, "Anonymous"); // Save the new highscore with no name
                }
            });
            dialog.show();
        }
    }

    private void saveHighScore(int score, String name) { // Overwrites highscore file with the new highscore
        try (FileWriter hsfw = new FileWriter("SnakeHighScore.txt", false)) {
            BufferedWriter hsbw = new BufferedWriter(hsfw);
            hsbw.write(score + "," + name);
            hsbw.close();
            hsfw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}