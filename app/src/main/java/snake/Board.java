package snake;

public class Board {
    private Object[][] board;
    private SnakeObject snake;

    public Board(int n, int m) {
        this.board = new Object[n][m];
        this.snake = new SnakeObject(n, m);
    }
}