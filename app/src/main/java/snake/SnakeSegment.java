package snake;

public class SnakeSegment {
    private int x;
    private int y;
    private int hSize;
    private int vSize;
    public SnakeSegment (int x, int y, int hSize, int vSize) {
        this.x=x;
        this.y=y;
        this.hSize=hSize;
        this.vSize=vSize;
    } 


    public void move(int dir) {
        switch (dir) {
            case 0:
                this.y=(this.vSize+(y-1))%this.vSize;
            case 1:
                this.x=(this.x+1)%this.hSize;
            case 2:
                this.y=(this.y+1)%this.vSize;
            case 3:
                this.x=(this.hSize+(x-1))%this.hSize;
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }


    //move to specific coordinates (wormwhole)
}