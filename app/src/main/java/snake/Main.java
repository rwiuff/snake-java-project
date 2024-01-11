package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;
    private static int width;
    private static int height;

    public static void main(String[] args) {
        String[] input = args.length < 2 ? new String[] { "20", "20" } : args;
        if (input[0].matches("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$")) {
            width = Integer.parseInt(input[0]);
            System.out.println("Width: " + width);
        } else {
            width = 20;
            System.out.println("Invalid width. Width set to: " + width);
        }
        if (input[1].matches("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$")) {
            height = Integer.parseInt(input[1]);
            System.out.println("Height: " + height);
        } else {
            height = 20;
            System.out.println("Invalid width. Height set to: " + height);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        primaryStage.setTitle("Welcome to snek");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        boardcontroller.run(scene);
        primaryStage.show();
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public static void gameOver(int score) {
        System.out.println("Game Over");
        System.out.println("Score: " + score);
        System.exit(0);
    }
}