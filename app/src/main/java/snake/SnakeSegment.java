package snake;
import javafx.scene.paint.Color;
public class SnakeSegment extends Space{    
    
    public SnakeSegment (int x, int y) {
        super(x, y);
        setColor(Color.ORANGE);
    }

    public boolean collision(SnakeObject snake){
        Main.gameOver((snake.getLength()-2) * 10);
        return false;
    }
}