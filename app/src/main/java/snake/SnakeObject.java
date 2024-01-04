package snake;

import java.util.ArrayList;

public class SnakeObject{
    private SnakeHead head;
    private ArrayList<SnakeSegment> body = new ArrayList<SnakeSegment>();
    private int length;
    private int boardSize;

    public SnakeObject(int vSize, int hSize) {
        this.head = new SnakeHead(vSize / 2, hSize / 2, hSize, vSize);
        this.body.add(new SnakeSegment(vSize / 2, (hSize / 2)+1));
        this.length = 2;
        this.boardSize = hSize*vSize;
    }

    public void extend() {
        this.body.add(0, new SnakeSegment(body.get(0).getX(), body.get(0).getY()));
        this.length++;
        if (boardSize == length) {
            System.out.println("You win!");
        }
    }

    public void snakeMove(){
        body.add(new SnakeSegment(head.getX(), head.getY()));
        head.move();
        body.remove(0);
    }

    public SnakeHead getHead(){
        return head;
    }

    public int getLength(){
        return length;
    }

    public ArrayList<SnakeSegment> getBody(){
        return body;
    }

}
