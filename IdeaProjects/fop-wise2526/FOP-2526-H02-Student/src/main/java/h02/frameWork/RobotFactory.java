package h02.frameWork;

import fopbot.Direction;
import fopbot.Robot;

import static h02.frameWork.RandomNumberGenerator.getRandomNumber;

import java.awt.*;

public class RobotFactory {

    private final AbstractWorld world;

    /**
     * constructor
     *
     * @param world this param helps the factory to place robots on free spots only
     */
    public RobotFactory(AbstractWorld world){
        this.world = world;
    }

    /**
     * creates robots with
     * - random direction
     * - random position
     * - 1 coin
     *
     * @return robot
     */
    public Robot createRobot() {
        Point coordinates = world.getRandomStartCoordinates();

        Direction rndDirection = getRandomDirection();

        return new Robot(coordinates.x,  coordinates.y, rndDirection, 1);
    }

    /**
     * generates a random direction
     *
     * @return 1 of 4 possible directions
     */
    private Direction getRandomDirection() {
        return switch (getRandomNumber(4)) {
            case 0 -> Direction.UP;
            case 1 -> Direction.RIGHT;
            case 2 -> Direction.DOWN;
            default -> Direction.LEFT;
        };
    }
}
