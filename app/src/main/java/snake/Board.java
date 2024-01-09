package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
    public Space[][] board;
    private SnakeObject snake;
    private ArrayList<Point> emptySpaces = new ArrayList<Point>(); // list of all spaces in the board array not containing an object
    private Random random = new Random();
    private Set<Point> changesMap = new HashSet<Point>(); // a set of the position on the board that need to be drawn by the BoarcController

    public Board(int n, int m, boolean wallsON, boolean warpsOn) {
        // instantiates a 2d Space array and the snake
        this.board = new Space[n][m];
        this.snake = new SnakeObject(n, m);
        placeSnake();

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                if (this.board[row][column] == null) {
                    emptySpaces.add(new Point(row, column));
                }
            }
        }

        if (wallsON) {
            snake.changeWinCondition(Wall.placeWalls(this.board, this.emptySpaces));
        }
        if (warpsOn) {
            snake.changeWinCondition(Warp.placeWarp(this.board, this.emptySpaces));
        }

        placeApple();
    }

    public Set<Point> update() {

        SnakeSegment tail = this.snake.getTail(); // -2 as length includes head,

        this.board[tail.getX()][tail.getY()] = null;
        Point tailPlace = new Point(tail.getX(), tail.getY());
        this.changesMap.add(tailPlace);
        this.changesMap.add(new Point(this.snake.getHead().getX(), this.snake.getHead().getY()));

        this.emptySpaces.add(tailPlace); // objects can be placed
        snake.snakeMove();
        Point headPlace = new Point(this.snake.getHead().getX(), this.snake.getHead().getY());

        this.emptySpaces.remove(new Point(this.snake.getTail().getX(), this.snake.getTail().getY()));
        this.emptySpaces.remove(headPlace);

        try {
            switch (this.board[snake.getHead().getX()][snake.getHead().getY()].collision(snake)) {
                case 0:
                    break; // essentially default. Just stops program from checking other cases.
                case 1: // apple
                    Point ghostTailPlace = new Point(this.snake.getGhostTail().getX(),
                            this.snake.getGhostTail().getY());
                    this.emptySpaces.remove(ghostTailPlace);
                    this.changesMap.add(ghostTailPlace);
                    this.changesMap.remove(tailPlace);
                    placeApple();
                    break;
                case 2: // warp
                    if (board[snake.getHead().getX()][snake.getHead().getY()].collision(snake) == 1) { // hits apple
                                                                                                       // after warp
                        placeApple();
                    }
                    break;

            }
        } catch (NullPointerException e) {
            // pass
        }
        this.changesMap.add(new Point((int) snake.getHead().getX(), (int) snake.getHead().getY()));

        placeSnake();
        return this.changesMap;
    }

    public void placeSnake() { // places the SnakeHead and newest segment in the board
        this.board[snake.getHead().getX()][snake.getHead().getY()] = snake.getHead();
        SnakeSegment newSnakeSegment = snake.getBody().get(snake.getBody().size() - 1);
        this.board[newSnakeSegment.getX()][newSnakeSegment.getY()] = newSnakeSegment;
    }

    public void placeApple() { // places an Apple object on a random empty spcae on the board
        int index = random.nextInt(emptySpaces.size());
        int x = (int) this.emptySpaces.get(index).getX();
        int y = (int) this.emptySpaces.get(index).getY();
        this.board[x][y] = new Apple(x, y);
        this.emptySpaces.remove(new Point(x, y));
        changesMap.add(new Point(x, y));
    }

    public SnakeObject getSnake() {
        return this.snake;
    }

    public Space[][] getBoard() {
        return this.board;
    }
}