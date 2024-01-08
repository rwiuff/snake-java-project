package snake;

import java.awt.Point;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class Warp extends Space {
    private Point warpPoint;
    static Board board;

    public Warp(int x, int y, Point warpPoint, Board gameBoard) {
        super(x, y);
        this.warpPoint = warpPoint;
        setColor(Color.PURPLE);
        board = gameBoard;
    }

    public int collision(SnakeObject snake) {
        snake.getHead().setX((int) warpPoint.getX());
        snake.getHead().setY((int) warpPoint.getY());
        snake.getHead().move();
        return 2;
    }

    public static void placeWarp(Space[][] spaceArray, ArrayList<Point> emptySpaces) {
        Random rng = new Random();
        int index = rng.nextInt(emptySpaces.size());
        Point first = emptySpaces.remove(index);
        index = rng.nextInt(emptySpaces.size());
        Point second = emptySpaces.remove(index);
        Warp warp1 = new Warp((int) first.getX(), (int) first.getY(), second, board);
        Warp warp2 = new Warp((int) second.getX(), (int) second.getY(), first, board);

        spaceArray[warp1.getX()][warp1.getY()] = warp1;
        spaceArray[warp2.getX()][warp2.getY()] = warp2;
    }

}
