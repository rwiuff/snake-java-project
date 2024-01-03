package snake;

public class SnakeHead extends Space{
    private int dir;
    private int vSize;
    private int hSize;

    
    public SnakeHead (int x, int y, int vSize, int hSize) {
        super(x, y);
        this.dir=3;
        this.hSize = hSize;
        this.vSize = vSize;
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
                setY((vSize+(getY()-1))%vSize);
                break;
            case 1:
                setX((getX()+1)%hSize);
                break;
            case 2:
                setY((getY()+1)%vSize);
                break;
            case 3:
                setX((hSize+(getX()-1))%hSize);
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}
