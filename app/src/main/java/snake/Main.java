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

    public static void main(String[] args) {
        dimensions = args.length < 2 ? new String[] { "20", "20" } : args;
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Snek");
        primaryStage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("/icons/icon16.png")),
                new Image(getClass().getResourceAsStream("/icons/icon32.png")),
                new Image(getClass().getResourceAsStream("/icons/icon64.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }

    public static void exit(Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit Snake?");
        alert.setHeaderText("You are about to exit Snake");
        alert.setTitle("Exit snake");
        ImageView graphic = new ImageView(new Image(Main.class.getResourceAsStream("/icons/icon32.png")));
        alert.setGraphic(graphic);
        if (alert.showAndWait().get() == ButtonType.OK)
            Platform.exit();
    }

    public void startGame(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        Parent root = loader.load();
        BoardController boardcontroller = loader.getController();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int screenWidth = (int) screenBounds.getWidth();
        int screenHeight = (int) screenBounds.getHeight();
        int fieldSize = 20;
        if ((width * 20) > screenWidth * 0.7 || (height * 20) > screenHeight * 0.7) {
            int heightSize = (int) (screenWidth * 0.7 / width);
            int widthSize = (int) (screenHeight * 0.7 / height);
            fieldSize = height > widthSize ? widthSize : heightSize;
        }
        boardcontroller.setDimensions(width, height, fieldSize);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        boardcontroller.run(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public static void gameOver() {
        System.out.println("Game Over");
    }
}