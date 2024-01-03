package snake;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
public class Apple extends Space{
    
    public Apple(int x, int y){
        super(x, y);
    }

    
    public boolean collision(SnakeObject snake) {
        snake.extend();
        return true;
    }
    public void placeNew (Space[][] spaceArray,ArrayList<Point> emptySpaces) {
        int index =new Random().nextInt(emptySpaces.size());
        Point place = emptySpaces.get(index);
        spaceArray[(int)place.getX()][(int)place.getY()]=new Apple((int)place.getX(),(int)place.getY());
    }
}
