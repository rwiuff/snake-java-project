package snake;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;

    public static void main(String[] args) {
        System.out.println(args.length);
        dimensions = args.length < 2 ? new String[] { "15", "15" } : args;
        System.out.println("Width: " + dimensions[0]);
        System.out.println("Height: " + dimensions[1]);
        dimensions = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome to snek");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        View view = new View(width, height);
        primaryStage.setScene(view.gridScene());
        primaryStage.show();
    }

}