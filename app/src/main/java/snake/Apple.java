package snake;

public class Apple extends Space{
    
    public Apple(int x, int y){
        super(x, y);
    }

    
    public void collision(SnakeObject snake) {
        snake.extend();
        //Board.placeApple();
    }
}
