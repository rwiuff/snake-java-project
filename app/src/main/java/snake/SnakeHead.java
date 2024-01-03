package snake;

public class SnakeHead extends Space{
    private int dir;
    private int x;
    private int y;
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
                this.y = ((vSize+(y-1))%vSize);
            case 1:
                this.x = ((x+1)%hSize);
            case 2:
                this.y = ((y+1)%vSize);
            case 3:
                this.x = ((hSize+(x-1))%hSize);
        }
    }
}
