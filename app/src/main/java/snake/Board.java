package snake;

import java.awt.Point;
import java.util.HashSet;

public class Board {
    private Space[][] board;
    private SnakeObject snake;
    private Apple apple;
    private HashSet<Point> emptySpaces = new HashSet<Point>();


    public Board(int n, int m) {
        this.board = new Space[n][m];
        this.snake = new SnakeObject(n, m);
        this.apple = new Apple(1, 2);
        update();
    }

    public void update(){
        try {
            this.board[this.snake.getHead().getX()][this.snake.getHead().getY()].collision(this.snake);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void collision(int x, int y, SnakeObject snek){

    }
}