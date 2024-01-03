package snake;

public class SnakeHead extends SnakeSegment {
    private int dir;

    public SnakeHead (int startX, int startY, int hSize, int vSize) {
        super(startX,startY,hSize,vSize);
        this.dir=3;
    }
    
    public void setDir(int tempDir) {
        this.dir=tempDir;
    }
    
    public int getDir() {
        return this.dir;
    }
    
}

