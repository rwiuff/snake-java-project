package snake;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class View {
private int width;
private int height;
    public View(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scene gridScene(){
        GridPane gridPane = new GridPane();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Rectangle r = new Rectangle();
                // r.setX(50);
                // r.setY(50);
                r.setWidth(10);
                r.setHeight(10);
                // r.setArcWidth(20);
                // r.setArcHeight(20);
                r.setFill(Color.OLIVE);
                r.setStroke(Color.WHEAT);
                gridPane.add(r, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(new Label("SNEK!"), gridPane, new Button("Menu"));
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        return new Scene(vbox, width*10+50, height*10+100);
    }
    
}