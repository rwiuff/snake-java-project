package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String[] dimensions;

    public static void main(String[] args) {
        System.out.println(args.length);
        dimensions = args.length < 2 ? new String[] { "15", "15" } : args;
        System.out.println("Width: " + dimensions[0]);
        System.out.println("Height: " + dimensions[1]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        // System.out.println("Width: " + width);
        // System.out.println("Height: " + height);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
        Parent root = loader.load();
        BoardController boardcontroller = loader.getController();
        boardcontroller.setDimensions(width, height);
        primaryStage.setTitle("Welcome to snek");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("Breakpoint");
    }

}