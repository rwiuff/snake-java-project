package snake;

public class SnakeSegment extends Space{
    public SnakeSegment (int x, int y) {
        super(x, y);
    }

    public boolean collision(SnakeObject snake){
        return false;
        // TODO: View.endGame();
    }
}