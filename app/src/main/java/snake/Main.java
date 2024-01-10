package snake;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;
    public static int width;
    public static int height;
    private static FXMLLoader menuLoader;
    private static FXMLLoader boardLoader;
    private static MainMenuController mainMenuController;
    public static BoardController boardController;
    private static Parent mainMenuRoot;
    private static Parent boardRoot;
    private static Image icon16;
    private static Image icon32;
    private static Image icon64;
    private static Scene scene;
    private static Settings settings;

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings settings) {
        Main.settings = settings;
    }

    public static void main(String[] args) {
        dimensions = args.length < 2 ? new String[] { "20", "20" } : args;
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
        settings = new Settings();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException { 
        populateResources();
        scene = new Scene(mainMenuRoot);
        primaryStage.setTitle("Snek");
        // primaryStage.setFullScreenExitHint("Press F11 to exit fullscreen");
        primaryStage.getIcons().addAll(icon16, icon32, icon64);
        mainMenu(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }

    public static void mainMenu(Stage primaryStage) {
        scene.setRoot(mainMenuRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    private void populateResources() throws IOException {
        menuLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        mainMenuController = menuLoader.getController();
        mainMenuRoot = menuLoader.load();
        boardLoader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        boardRoot = boardLoader.load();
        boardController = boardLoader.getController();
        icon16 = new Image(getClass().getResourceAsStream("/icons/icon16.png"));
        icon32 = new Image(getClass().getResourceAsStream("/icons/icon32.png"));
        icon64 = new Image(getClass().getResourceAsStream("/icons/icon64.png"));
    }

    private static void boardReLoad() throws IOException {
        boardLoader = new FXMLLoader(Main.class.getResource("/fxml/Board.fxml"));
        boardRoot = boardLoader.load();
        boardController = boardLoader.getController();
    }

    public static void exit(Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit Snake?");
        alert.setHeaderText("You are about to exit Snake");
        alert.setTitle("Exit snake");
        alert.setGraphic(new ImageView(icon32));
        if (alert.showAndWait().get() == ButtonType.OK)
            Platform.exit();
    }

    public static void startGame(Stage primaryStage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int screenWidth = (int) screenBounds.getWidth();
        int screenHeight = (int) screenBounds.getHeight();
        int fieldSize = 20;
        if ((width * 20) > screenWidth * 0.7 || (height * 20) > screenHeight * 0.7) {
            int heightSize = (int) (screenWidth * 0.7 / width);
            int widthSize = (int) (screenHeight * 0.7 / height);
            fieldSize = height > widthSize ? widthSize : heightSize;
        }
        boardReLoad();
        scene.setRoot(boardRoot);
        boardController.setDimensions(width, height, fieldSize);
        boardController.setSettings(settings);
        boardController.setup(scene);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public static void gameOver(int length) {
        boardController.gameOver((length-2) * 100);
    }

    // public static void resize(Stage stage) {
    //     Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    //     int screenWidth = (int) screenBounds.getWidth();
    //     int screenHeight = (int) screenBounds.getHeight();
    //     if (stage.isFullScreen()) {
    //         int newWidth = screenWidth / width;
    //         int newHeight = screenHeight / height;
    //         fieldSize = newWidth > newHeight ? (int) (newHeight * 0.9) : (int) (newWidth * 0.9);
    //     } else {
    //         if ((width * 20) > screenWidth * 0.7 || (height * 20) > screenHeight * 0.7) {
    //             int heightSize = (int) (screenWidth * 0.7 / width);
    //             int widthSize = (int) (screenHeight * 0.7 / height);
    //             fieldSize = height > widthSize ? widthSize : heightSize;
    //         }
    //     }
    //     boardController.setDimensions(screenWidth, width, height);
    //     boardController.drawBoard();
    // }

}