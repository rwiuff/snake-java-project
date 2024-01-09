package snake;

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

    public int  collision(SnakeObject snake) { // int to determine what it collides with
        return 0;
    }


    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
