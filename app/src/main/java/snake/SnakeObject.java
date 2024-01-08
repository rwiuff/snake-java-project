package snake;

import java.util.ArrayList;

public class SnakeObject {
    private SnakeHead head;
    private ArrayList<SnakeSegment> body = new ArrayList<SnakeSegment>();
    private int length;
    private int winCondition;
    private SnakeSegment ghostTail;

    public SnakeObject(int vSize, int hSize) {
        this.head = new SnakeHead(vSize / 2, hSize / 2, hSize, vSize);
        this.body.add(new SnakeSegment(vSize / 2, (hSize / 2) + 1));
        this.ghostTail = new SnakeSegment(vSize / 2, hSize / 2 + 2);
        this.length = 2;
        this.winCondition = hSize * vSize;
    }

    public void extend() {
        this.body.add(0, this.ghostTail);
        this.length++;
        if (this.winCondition == length) {
            Main.gameOver(length*10);
        }
    }

    public void snakeMove() {
        body.add(new SnakeSegment(head.getX(), head.getY()));
        head.move();
        this.ghostTail = body.remove(0);
    }

    public SnakeHead getHead() {
        return head;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<SnakeSegment> getBody() {
        return body;
    }

    public SnakeSegment getTail() {
        return this.body.get(0);
    }

    public SnakeSegment getGhostTail() {
        return this.ghostTail;
    }

    public void changeWinCondition(int amount) {
        this.winCondition =- amount;
    }
}
