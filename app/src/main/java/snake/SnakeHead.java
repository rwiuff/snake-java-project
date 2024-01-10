package snake;

import javafx.scene.paint.Color;

public class SnakeHead extends Space{
    private int dir;
    private int vSize;
    private int hSize;

    
    public SnakeHead (int x, int y, int hSize, int vSize) {
        super(x, y);
        this.dir=3;
        this.hSize = hSize;
        this.vSize = vSize;
        setColor(Color.BLACK);
    }

    public int getDir() {
        return this.dir;
    }
    public void setDir(int dir) {
        if (!(dir%2 == this.dir%2)) {
            this.dir = dir;
        }
    }

    public void move() {
        switch (dir) {
            case 0:
                setX((vSize+(getX()-1))%vSize);
                break;
            case 1:
                setY((getY()+1)%hSize);
                break;
            case 2:
                setX((getX()+1)%vSize);
                break;
            case 3:
                setY((hSize+(getY()-1))%hSize);
                break;
            default:
                System.out.println("default");
                break;
        }
    }
    
}
