package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
    public Space[][] board;
    private SnakeObject snake;
    private ArrayList<Point> emptySpaces = new ArrayList<Point>();
    private Random random = new Random();
    private Set<Point> changesSet = new HashSet<Point>();
    

    public Board(int n, int m) {
        this.board = new Space[n][m];
        this.snake = new SnakeObject(n, m);
        this.board[this.snake.getHead().getX()][this.snake.getHead().getY()] = this.snake.getHead();
        this.board[this.snake.getTail().getX()][this.snake.getTail().getY()] = snake.getTail();
        

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                if (this.board[row][column] == null) {
                    emptySpaces.add(new Point(row, column));
                }
            }
        }
        placeApple();
    }

    public Set<Point> update() {
        this.changesSet.clear();
        SnakeSegment tail = this.snake.getTail(); 
        
        this.board[tail.getX()][tail.getY()] = null;
        Point tailPlace = new Point(tail.getX(), tail.getY());
        changesSet.add(tailPlace);
        changesSet.add(new Point(this.snake.getHead().getX(), this.snake.getHead().getY()));
        
        this.emptySpaces.add(tailPlace); // objects can be placed
        snake.snakeMove();
        Point headPlace = new Point(this.snake.getHead().getX(), this.snake.getHead().getY());
        changesSet.add(headPlace);
        this.emptySpaces.remove(new Point (this.snake.getTail().getX(),this.snake.getTail().getY()));
        this.emptySpaces.remove(headPlace);
        
        try {
            if (this.board[snake.getHead().getX()][snake.getHead().getY()].collision(snake)) { //true if has to place a new of its type
                Point ghostTailPlace = new Point(this.snake.getGhostTail().getX(),this.snake.getGhostTail().getY());
                this.emptySpaces.remove(ghostTailPlace);
                changesSet.add(ghostTailPlace);
                changesSet.remove(tailPlace);
                placeApple();
            }

        } catch (NullPointerException e) {
            // pass
        }
        placeSnake();
        return changesSet;
    }

    public void placeSnake() {
        this.board[snake.getHead().getX()][snake.getHead().getY()] = snake.getHead();
        SnakeSegment newSnakeSegment = snake.getBody().get(snake.getBody().size()-1);
        this.board[newSnakeSegment.getX()][newSnakeSegment.getY()] = newSnakeSegment;
        
    }

    public void placeApple() {
        int index = random.nextInt(emptySpaces.size());
        int x = (int) this.emptySpaces.get(index).getX();
        int y = (int) this.emptySpaces.get(index).getY();
        this.board[x][y] = new Apple(x, y);
        Point applePlace = new Point(x,y);
        this.emptySpaces.remove(applePlace);
        this.changesSet.add(applePlace);
        
    }

    public SnakeObject getSnake() {
        return this.snake;
    }

    public Space[][] getBoard() {
        return this.board;
    }

}