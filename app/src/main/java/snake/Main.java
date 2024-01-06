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
    private static MainMenuController mainMenuController;
    private static BoardController boardController;
    private static Parent mainMenuRoot;
    private static Parent boardRoot;
    private static Image icon16;
    private static Image icon32;
    private static Image icon64;

    public static void main(String[] args) {
        dimensions = args.length < 2 ? new String[] { "20", "20" } : args;
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        populateResources();
        primaryStage.setTitle("Snek");
        primaryStage.getIcons().addAll(icon16, icon32, icon64);
        primaryStage.setScene(new Scene(mainMenuRoot));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }

    private void populateResources() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        mainMenuController = menuLoader.getController();
        mainMenuRoot = menuLoader.load();
        FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        boardRoot = boardLoader.load();
        boardController = boardLoader.getController();
        icon16 = new Image(getClass().getResourceAsStream("/icons/icon16.png"));
        icon32 = new Image(getClass().getResourceAsStream("/icons/icon32.png"));
        icon64 = new Image(getClass().getResourceAsStream("/icons/icon64.png"));

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

    public static void startGame(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int screenWidth = (int) screenBounds.getWidth();
        int screenHeight = (int) screenBounds.getHeight();
        int fieldSize = 20;
        if ((width * 20) > screenWidth * 0.7 || (height * 20) > screenHeight * 0.7) {
            int heightSize = (int) (screenWidth * 0.7 / width);
            int widthSize = (int) (screenHeight * 0.7 / height);
            fieldSize = height > widthSize ? widthSize : heightSize;
        }
        boardController.setDimensions(width, height, fieldSize);
        Scene scene = new Scene(boardRoot);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        boardController.run(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }
    public static void gameOver(int score) {
        System.out.println("Game Over");
        System.out.println("Score: "+ score);
    }
}