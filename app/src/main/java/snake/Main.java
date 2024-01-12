package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static int width;
    private static int height;

    public static void main(String[] args) {
        String[] input = args.length < 2 ? new String[] { "20", "20" } : args; // Test for valid input string
        if (input[0].matches("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$")) { // Validate input for natural numbers
            width = Integer.parseInt(input[0]);
            System.out.println("Width: " + width);
        } else { // Invalid input results in error message to the console and a default width
            width = 20;
            System.out.println("Invalid width. Width set to: " + width);
        }
        if (input[1].matches("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$")) { // Validate input for natural numbers
            height = Integer.parseInt(input[1]);
            System.out.println("Height: " + height);
        } else { // Invalid input results in error message to the console and a default width
            height = 20;
            System.out.println("Invalid width. Height set to: " + height);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        Parent root = loader.load();
        BoardController boardcontroller = loader.getController(); // Save BoardController object in a field
        Rectangle2D screenBounds = Screen.getPrimary().getBounds(); // Get physical screen dimensions
        int screenWidth = (int) screenBounds.getWidth();
        int screenHeight = (int) screenBounds.getHeight();
        int fieldSize = 20;
        if ((width * 20) > screenWidth * 0.7 || (height * 20) > screenHeight * 0.7) { // Test if the board exceeds
                                                                                      // physical screen resolution
            int heightSize = (int) (screenWidth * 0.7 / width);
            int widthSize = (int) (screenHeight * 0.7 / height);
            fieldSize = height > widthSize ? widthSize : heightSize; // Reduce discrete board element 'Field', to keep
                                                                     // board size inside physical screen
        }
        boardcontroller.setDimensions(width, height, fieldSize); // Parse resolution fields to the board
        primaryStage.setTitle("Welcome to snek");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        boardcontroller.run(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public static void gameOver(int score) {
        System.out.println("Game Over");
        System.out.println("Score: " + score);
        System.exit(0);
    }
}