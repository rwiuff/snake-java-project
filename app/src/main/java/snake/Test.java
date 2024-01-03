package snake;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Board board = new Board(10, 10);

        while(true){
            System.out.println(board);
            int dir = console.nextInt();
            board.getSnake().getHead().setDir(dir);
            board.update();
        }
    }
}
