package snake;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
public class Apple extends Space{
    public Apple(int x, int y){
        super(x, y);
        setColor(Color.RED);
    }

    
    public boolean collision(SnakeObject snake) {
        snake.extend();
        return true;
    }
    public void placeNew (Space[][] spaceArray,ArrayList<Point> emptySpaces) {
        int index =new Random().nextInt(emptySpaces.size());
        Point place = emptySpaces.get(index);
        emptySpaces.remove(place);
        spaceArray[(int)place.getX()][(int)place.getY()]=new Apple((int)place.getX(),(int)place.getY());
    }
}
