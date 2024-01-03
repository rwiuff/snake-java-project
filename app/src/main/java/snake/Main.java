package snake;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;

    public static void main(String[] args) {
        System.out.println("Width: " + args[0]);
        System.out.println("Height: " + args[1]);
        dimensions = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome to snek");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        GridPane gridPane = new GridPane();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle r = new Rectangle();
                // r.setX(50);
                // r.setY(50);
                r.setWidth(50);
                r.setHeight(50);
                // r.setArcWidth(20);
                // r.setArcHeight(20);
                r.setFill(Color.OLIVE);
                r.setStroke(Color.WHEAT);
                gridPane.add(r, i, j);
            }
        }
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}