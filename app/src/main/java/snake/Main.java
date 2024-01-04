package snake;

import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;

    public static void main(String[] args) {
        dimensions = args.length < 2 ? new String[] { "20", "20" } : args;
        System.out.println("Width: " + dimensions[0]);
        System.out.println("Height: " + dimensions[1]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        Parent root = loader.load();
        BoardController boardcontroller = loader.getController();
        boardcontroller.setDimensions(width, height);
        primaryStage.setTitle("Welcome to snek");
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
        primaryStage.setScene(scene);
        boardcontroller.run(scene);
        primaryStage.show();
        // double x = primaryStage.getX();
        // double y = primaryStage.getY();
        // double xD = primaryStage.getWidth();
        // double yD = primaryStage.getHeight();
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        // primaryStage.setX(x + ((xD - primaryStage.getWidth()) / 2));
        // primaryStage.setY(y + ((yD - primaryStage.getHeight()) / 2));
        System.out.println("Breakpoint");

    }

}