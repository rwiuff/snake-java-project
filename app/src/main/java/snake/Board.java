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
        this.board[snake.getBody().get(0).getX()][snake.getBody().get(0).getY()] = null;
        snake.snakeMove();
        try {
            this.board[snake.getHead().getX()][snake.getHead().getY()].collision(snake);
            
        } catch (Exception e) {
            // pass
        }
        placeSnake();
        // TODO: draw snake in view
    }

    public void placeSnake(){
        this.board[snake.getHead().getX()][snake.getHead().getY()] = snake.getHead();
        for (SnakeSegment snakeBody : snake.getBody()) {
            this.board[snakeBody.getX()][snakeBody.getY()] = snakeBody;
        }
    }

    public static void placeApple(){

    }
    
    public SnakeObject getSnake() {
        return this.snake;
    }
}