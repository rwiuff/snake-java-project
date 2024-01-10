package snake;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.awt.Point;
public class Bomb extends Space{

    private int expirationtime;
    

    public Bomb (int x, int y, int expirationtime) {
        super(x,y);
        setColor(Color.CHARTREUSE);
        this.expirationtime=expirationtime;

    }

    public int collision (SnakeObject snake) {
        snake.shorten();
        return 3;
    }
   
    public boolean checkExpiration(Space[][] spaceArray,ArrayList<Point> emptySpaces) {
        if (this.expirationtime==0) {
            spaceArray[this.getX()][this.getY()]=null;
            emptySpaces.add(new Point(this.getX(),this.getY()));
            return true; //to remove from bomblist
        }
        this.expirationtime--; //checked each update, and decrements as such
        return false; //keep in bomblist
    }
}