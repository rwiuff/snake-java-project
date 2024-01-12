package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
    public Space[][] board;
    private int boardSize;
    private int height;
    private int width;
    private SnakeObject snake;
    private ArrayList<Point> emptySpaces = new ArrayList<Point>(); // list of all coordinates in the space array not containing an object
    private Random random = new Random();
    private Set<Point> changesMap = new HashSet<Point>(); // set of coodinates in the space array that need to be redrawn
    private Set<Point> bombList = new HashSet<Point>();

    public Board(int n, int m, boolean wallsON, boolean warpsOn) {
        // instantiates a 2d Space array and the snake
        this.board = new Space[n][m];
        this.height = n;
        this.width = m;
        this.snake = new SnakeObject(n, m);
        this.boardSize = n * m;
        placeSnake();

        // finds all empty spaces on the board
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                if (this.board[row][column] == null) {
                    emptySpaces.add(new Point(row, column));
                }
            }
        }

        if (wallsON) { // places walls and changes the win condition by the amount of walls
            snake.changeWinCondition(Wall.placeWalls(this.board, this.emptySpaces));
        }
        if (warpsOn) { // places warps and changes the win condition by the amount of warps
            snake.changeWinCondition(Warp.placeWarp(this.board, this.emptySpaces));
        }

        placeApple();
    }

    public Set<Point> update() {

        SnakeSegment tail = this.snake.getTail();

        // removes the tails position from the board, and adds it as an empty space
        this.board[tail.getX()][tail.getY()] = null;
        Point tailPlace = new Point(tail.getX(), tail.getY());
        this.changesMap.add(tailPlace);
        this.emptySpaces.add(tailPlace); 

        this.changesMap.add(new Point(this.snake.getHead().getX(), this.snake.getHead().getY())); // makes sure the olds head position is redrawn
       
        snake.snakeMove(); // moves the snake

        // removes the tail and new head from the available positions
        Point headPlace = new Point(this.snake.getHead().getX(), this.snake.getHead().getY());
        this.emptySpaces.remove(new Point(this.snake.getTail().getX(), this.snake.getTail().getY()));
        this.emptySpaces.remove(headPlace);

        // checks for the collision of the head
        try {
            switch (this.board[snake.getHead().getX()][snake.getHead().getY()].collision(snake)) {
                case 0:
                    break; // essentially default. Just stops program from checking other cases.
                case 1: // collided with apple
                    Point ghostTailPlace = new Point(this.snake.getGhostTail().getX(),
                            this.snake.getGhostTail().getY());
                    this.emptySpaces.remove(ghostTailPlace);
                    this.changesMap.add(ghostTailPlace);
                    this.changesMap.remove(tailPlace);
                    placeApple();
                    break;
                case 2: // collided with warp
                    if (board[snake.getHead().getX()][snake.getHead().getY()].collision(snake) == 1) { // hits apple after warp
                        placeApple();
                    }
                    break;
                case 3: // collided with bomb
                    SnakeSegment newGhostTail = this.snake.getGhostTail(); // segment with coordinates of new ghostTail
                    this.board[newGhostTail.getX()][newGhostTail.getY()] = null;
                    Point newTailPoint = new Point(newGhostTail.getX(), newGhostTail.getY());
                    emptySpaces.add(newTailPoint);
                    changesMap.add(newTailPoint);

            }
        } catch (NullPointerException e) {
            // passes if doesn't hit an object
        }

        // adds the heads position to be redrawn
        changesMap.add(new Point((int) snake.getHead().getX(), (int) snake.getHead().getY()));
        // checking if bombs have expired
        Set<Point> expiredBombs = new HashSet<Point>();
        for (Point bombSite : this.bombList) {
            try { // only used to guarantee no error is thrown when trying method. Shouldnt occur
                  // as all points in list are places of bombs

                if (this.board[(int) bombSite.getX()][(int) bombSite.getY()].checkExpiration(this.board,
                        this.emptySpaces)) {
                    expiredBombs.add(bombSite);
                    changesMap.add(bombSite);
                }

            } catch (NullPointerException e) {
                // do nothing
            }
        }
        for (Point dud : expiredBombs) {
            this.bombList.remove(dud);
        }
        
        placeSnake();
        return this.changesMap;
    }

    public void placeSnake() { // places the SnakeHead and newest segment in the board array
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

    public void placeBomb(int expirationtime) {
        int headX = this.snake.getHead().getX();
        int headY = this.snake.getHead().getY();
        ArrayList<Point> checkedPoints = new ArrayList<Point>();
        boolean placeFound = false;
        int index;
        while (!placeFound) {
            index = this.random.nextInt(this.emptySpaces.size() - 1);
            Point tempPlace = this.emptySpaces.get(index);
            int tempX = (int) tempPlace.getX();
            int tempY = (int) tempPlace.getY();
            if (tempPlace.getX() != headX && tempPlace.getY() != headY) { // to avoid placing bomb directly infront of
                                                                          // player
                this.board[tempX][tempY] = new Bomb(tempX, tempY, expirationtime);
                Point bombPlace = new Point(tempX, tempY);
                this.bombList.add(bombPlace);
                this.changesMap.add(bombPlace);
                emptySpaces.remove(tempPlace);
                placeFound = true;
            } else {
                checkedPoints.add(tempPlace);
                emptySpaces.remove(tempPlace);
            }

        }
        // place now found
        emptySpaces.addAll(checkedPoints);

    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public int noOfEmptyspaces() {
        return this.emptySpaces.size();
    }

    public void clearChangesMap() {
        this.changesMap.clear();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

}