package snake;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;
import javafx.scene.paint.Color;
public class Wall extends Space{

    public Wall(int x, int y) {
        super(x,y);
        setColor(Color.BLACK);
    }

    public int collision(SnakeObject snake) {
        Main.gameOver((snake.getLength() - 2) * 10);
        return 0;
    }
    public static void placeWalls(Space[][] spaceArray, ArrayList<Point> emptySpaces) {
        Random rng = new Random();
        int amount = rng.nextInt(spaceArray.length*spaceArray[0].length/100*5);
        for (int i=1; i<=amount;i++) {
            int index=rng.nextInt(emptySpaces.size());
            Point coordinates = emptySpaces.get(index);
            int xCord=(int) coordinates.getX();
            int yCord= (int) coordinates.getY();
            boolean legal=true;
            for (int vertical=xCord-1;vertical<=xCord+1;vertical++) {
                for (int horizontal=yCord-1;horizontal<=yCord+1;horizontal++) {
                    if(!(emptySpaces.contains(new Point(vertical,horizontal))) && (vertical!=xCord || horizontal!=yCord)) {
                        legal=false;
                        break;
                    }
                }
            }
            if (legal) {
                spaceArray[xCord][yCord]=new Wall(xCord, yCord);
                emptySpaces.remove(coordinates);
            }

        }

    }

}
