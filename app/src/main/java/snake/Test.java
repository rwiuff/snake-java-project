package snake;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Board board = new Board(10, 5);

        while(true){
            System.out.println(board);
            int dir = console.nextInt();
            board.getSnake().getHead().setDir(dir);
            board.update();
        }
    }

    public static void endGame(int score){
        System.out.println("Game Over");
        System.out.println("Score: " + score);
        System.exit(0);
    }
}
