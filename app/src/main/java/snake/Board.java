package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    public Space[][] board;
    private SnakeObject snake;
    private ArrayList<Point> emptySpaces = new ArrayList<Point>();
    private Random random=new Random();
    
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
        this.board[this.snake.getHead().getX()][this.snake.getHead().getY()]=this.snake.getHead();
        this.board[this.snake.getBody().get(0).getX()][this.snake.getBody().get(0).getY()]=snake.getBody().get(0);
        for (int row =0; row<n;row++) {
            for (int column=0; column<m;column++) {
                if (this.board[row][column]==null) {
                    emptySpaces.add(new Point (row,column));
                }
            }
        }
        placeApple();
        update();

    }

    public void update(){
        this.board[snake.getBody().get(0).getX()][snake.getBody().get(0).getY()] = null;
        snake.snakeMove();
        try {
            if (this.board[snake.getHead().getX()][snake.getHead().getY()].collision(snake)) {
                this.board[snake.getHead().getX()][snake.getHead().getY()].placeNew(this.board,this.emptySpaces);
            };
            
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
        int index=random.nextInt(emptySpaces.size());
        int x =(int) this.emptySpaces.get(index).getX();
        int y= (int) this.emptySpaces.get(index).getY();
        this.board[x][y]=new Apple(x,y);
        this.emptySpaces.remove(new Point(x,y));
    }
    
    public SnakeObject getSnake() {
        return this.snake;
    }
}