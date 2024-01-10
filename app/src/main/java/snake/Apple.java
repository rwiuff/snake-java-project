package snake;

import javafx.scene.paint.Color;

public class Apple extends Space {
    public Apple(int x, int y) {
        super(x, y);
        setColor(Color.CRIMSON);
    }

    public int collision(SnakeObject snake) {
        snake.extend();
        return 1;
    }
}
