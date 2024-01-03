package snake;


public class Controller {
    private Board board;
    

    public Controller (Board board) {
        this.board =board;
    }
    public void changeDir (int dir) {
        this.board.getSnake().getHead().setDir(dir);
    }
    
}