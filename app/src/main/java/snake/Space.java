package snake;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.paint.Color;

abstract class Space {
    private int x;
    private int y;
    private Color color = Color.DARKORANGE;

    public Space(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int collision(SnakeObject snake) { // boolean only used for apple
        return 0;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean checkExpiration(Space[][] board, ArrayList<Point> emptySpaces) {
        return false;
    }

}
