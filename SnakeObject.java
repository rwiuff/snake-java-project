import java.util.ArrayList;

public class SnakeObject {
    private ArrayList<Integer> directions;
    private ArrayList<SnakeSegment> body;
    private SnakeHead head;
    private int hSize;
    private int vSize;

    public SnakeObject(int hSize, int vSize) {
        this.hSize = hSize;
        this.vSize = vSize;
        this.head = new SnakeHead(hSize / 2, vSize / 2, hSize, vSize);
        this.body.add(new SnakeSegment(hSize / 2 + 1, vSize / 2, hSize, vSize));
        this.directions.add(this.head.getDir());
    }

    public void extend() {
        this.body.add(new SnakeSegment(this.body.get(this.body.size() - 1).getX(),
                this.body.get(this.body.size() - 1).getY(), hSize, vSize));
        this.directions.add(this.directions.get(this.directions.size() - 1));
    }

}
