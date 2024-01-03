package snake;

public class SnakeSegment extends Space{
    public SnakeSegment (int x, int y) {
        super(x, y);
    }

    public boolean collision(SnakeObject snake){
        Test.endGame(snake.getLength() * 10);
        return false;
    }
}