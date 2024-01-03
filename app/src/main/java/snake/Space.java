package snake;
import java.awt.Point;
import java.util.ArrayList;
abstract class Space {
    private int x;
    private int y;

    public Space(int x, int y){
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


    public boolean collision(SnakeObject snake){ //boolean only used for apple
        return false;
    }
    public void placeNew(Space[][] spaceArray,ArrayList<Point> emptySpaces) {

    }


}
