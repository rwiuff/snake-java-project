package snake;
public class Wall extends Space{

    public Wall(int x, int y) {
        super(x,y);
    }

    public boolean collision(SnakeObject snake) {
        
        return false;
    }
    
}
