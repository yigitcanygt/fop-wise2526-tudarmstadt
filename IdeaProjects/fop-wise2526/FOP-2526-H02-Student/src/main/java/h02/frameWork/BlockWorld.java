package h02.frameWork;

import fopbot.Block;

import java.awt.*;
import java.util.LinkedList;

import static h02.frameWork.RandomNumberGenerator.getRandomNumber;


class BlockWorld extends AbstractWorld {

    protected final LinkedList<Block> listOfBlocks = new LinkedList<>();

    /**
     * constructs an obstacle world
     * this world has blocks that block the path of a robot and 4 destination fields.
     * The blocks are never placed adjacent to each other, neither directly nor diagonally.
     *
     * @param worldSize world size
     */
    public BlockWorld(int worldSize) {
        super(worldSize);
    }

    @Override
    protected void setupWorld() {
        putBlocks();
    }

    @Override
    protected void setDestinationField(){
        Color fieldColor = getDestinationFieldColor();
        getGlobalWorld().setFieldColor(0, 0, fieldColor);
        getGlobalWorld().setFieldColor(0, WORLD_SIZE - 1, fieldColor);
        getGlobalWorld().setFieldColor(WORLD_SIZE - 1, 0, fieldColor);
        getGlobalWorld().setFieldColor(WORLD_SIZE - 1, WORLD_SIZE - 1, fieldColor);
    }

    @Override
    protected Point getRandomStartCoordinates() {
        Point point;

        do {
            point = super.getRandomStartCoordinates();
        } while (isBlockedAt(point.x, point.y));

        return point;
    }

    /**
     * adds blocks on random positions
     */
    private void putBlocks(){
        int rndX;
        int rndY;

        for(int i = 0; i < 20; i++) {
            do {
                rndX = getRandomNumber(WORLD_SIZE);
                rndY = getRandomNumber(WORLD_SIZE);
            } while (isNearBlock(rndX, rndY) || isDestinationField(rndX, rndY));
            placeBlock(rndX, rndY);
            listOfBlocks.add(new Block(rndX,rndY));
        }
    }

    /**
     * checks if the position is occupied by a block
     *
     * @param x coordinate of the to be checked position
     * @param y coordinate of the to be checked position
     * @return true, if position is occupied
     */
    private boolean isBlockedAt(int x, int y){
        for(Block block : listOfBlocks){
            if(block.getX() == x && block.getY() == y)
                return true;
        }
        return false;
    }

    /**
     * checks if a block is near
     * directly beside and diagonal
     *
     * @param x coordinate of the new object
     * @param y coordinate of the new object
     * @return true, if a block is near
     */
    private boolean isNearBlock(int x, int y){
        for (Block block : listOfBlocks) {
            int blockX = block.getX();
            int blockY = block.getY();

            if ((blockX >= x - 1 && blockX <= x + 1) && (blockY >= y - 1 && blockY <= y + 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the coordinates are a destination field for the robot
     * to make sure that the robot can reach its destination
     *
     * @param x coordinate of the new object
     * @param y y coordinate of the new object
     * @return true, if it is a destination field
     */
    private boolean isDestinationField(int x, int y){
        return (x == 0 || x == WORLD_SIZE - 1) && (y == WORLD_SIZE - 1 || y == 0);
    }
}
