package snake;


public class Controller {
    private Board board;
    

    public Controller (Board board) {
        this.board =board;
    }
    public void changeDir (int dir) {
        // w-0
        // a-3
        // s-2
        //d-1
        this.board.getSnake().getHead().setDir(dir);
        this.board.update(); //as only updates when moving.
    }
    
}