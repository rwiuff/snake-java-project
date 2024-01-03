package snake;

import java.awt.Point;
import java.util.HashSet;

public class Board {
    public Space[][] board;
    private SnakeObject snake;
    private Apple apple;
    private HashSet<Point> emptySpaces = new HashSet<Point>();

    
    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Object obj = board[i][j];
                if (obj instanceof Apple) {
                    s += " Apple ";
                }
                else if (obj instanceof SnakeSegment) {
                    s += " Snake ";
                }
                else if (obj instanceof SnakeHead){
                    s += " Head  "; 
                }
                else {
                    s += "   0   ";
                }
            }
            s += "\n";
        }
        return s;
    }

    public Board(int n, int m) {
        this.board = new Space[n][m];
        this.snake = new SnakeObject(n, m);
        this.apple = new Apple(3, 3);
        placeSnake();
        placeApple();
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

    public void placeApple(){
        this.board[apple.getX()][apple.getY()] = apple;
    }
    
    public SnakeObject getSnake() {
        return this.snake;
    }
}