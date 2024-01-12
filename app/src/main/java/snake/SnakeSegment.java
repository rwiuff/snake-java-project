package snake;

public class SnakeSegment extends Space {

    public SnakeSegment(int x, int y) {
        super(x, y);
    }

    public int collision(SnakeObject snake) { // ends the game
        Main.gameOver(snake.getLength());
        return 0;
    }
}