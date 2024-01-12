package snake;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;
import javafx.scene.paint.Color;

public class Wall extends Space {

    public Wall(int x, int y) {
        super(x, y);
        setColor(Color.BLACK);
    }

    public int collision(SnakeObject snake) { // ends the game
        Main.gameOver(snake.getLength());
        return 0;
    }

    public static int placeWalls(Space[][] spaceArray, ArrayList<Point> emptySpaces) {
        Random rng = new Random();

        Point illegal1 = new Point(spaceArray.length / 2, spaceArray[0].length / 2 - 1); // directly infront of head
        Point illegal2 = new Point(spaceArray.length / 2, spaceArray[0].length / 2 - 2); // two in front of head
        // makes sure that the illegal spaces can't be chosen
        emptySpaces.remove(illegal1);
        emptySpaces.remove(illegal2); 

        // picks the amount of walls to be placed (max 5% of board size)
        int amount = rng.nextInt((int) (spaceArray.length * (double) spaceArray[0].length / 100 * 5));
        for (int i = 1; i <= amount; i++) {
            int index = rng.nextInt(emptySpaces.size());
            Point coordinates = emptySpaces.get(index);
            int xCord = (int) coordinates.getX();
            int yCord = (int) coordinates.getY();
            boolean legal = true;
            for (int vertical = xCord - 1; vertical <= xCord + 1; vertical++) {
                for (int horizontal = yCord - 1; horizontal <= yCord + 1; horizontal++) {
                    if (!(emptySpaces.contains(new Point(vertical, horizontal)))
                            && (vertical != xCord || horizontal != yCord)) {
                        legal = false;
                        break;
                    }
                }
            }
            if (legal) {
                spaceArray[xCord][yCord] = new Wall(xCord, yCord);
                emptySpaces.remove(coordinates);
            }

        }
        emptySpaces.add(illegal1);
        emptySpaces.add(illegal2);

        return amount;
    }

}
