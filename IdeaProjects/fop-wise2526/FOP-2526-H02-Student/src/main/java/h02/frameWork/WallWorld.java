package h02.frameWork;

import java.awt.*;

import static h02.frameWork.RandomNumberGenerator.getRandomNumber;

class WallWorld extends AbstractWorld {

    /**
     * constructs a random world
     * it has walls that divide the world in 4 equally sized areas with random wall-gaps
     *
     * @param worldSize world size
     */
    public WallWorld(int worldSize){
        super(worldSize);
    }

    @Override
    protected void setupWorld() {
        setWallGaps();
    }

    @Override
    protected void setDestinationField() {
        Color fieldColor1, fieldColor2, fieldColor3;
        fieldColor1 = getDestinationFieldColor();
        fieldColor2 = getDestinationFieldColor();
        fieldColor3 = getDestinationFieldColor();

        getGlobalWorld().setFieldColor(0, WORLD_SIZE - 1, fieldColor1);
        getGlobalWorld().setFieldColor(WORLD_SIZE - 1, 0, fieldColor2);
        getGlobalWorld().setFieldColor(WORLD_SIZE - 1, WORLD_SIZE - 1, fieldColor3);
    }

    /**
     * defines position of wall gaps and creates walls with those gaps
     * there is always one gap at an edge of the world
     * so students can test this special case more easily
     */
    private void setWallGaps(){
        int horizontalGap1, horizontalGap2, verticalGap1, verticalGap2;

        horizontalGap1 = getRandomNumber(WORLD_SIZE / 2);
        horizontalGap2 = getRandomNumber(WORLD_SIZE / 2) + WORLD_SIZE / 2;
        verticalGap1 = getRandomNumber(WORLD_SIZE / 2);
        verticalGap2 = getRandomNumber(WORLD_SIZE / 2) + WORLD_SIZE / 2;

        int rnd = getRandomNumber(4);
        switch (rnd){
            case 0 -> horizontalGap1 = 0;
            case 1 -> horizontalGap2 = WORLD_SIZE - 1;
            case 3 -> verticalGap1 = 0;
            default -> verticalGap2 = WORLD_SIZE -1;
        }

        putWalls(horizontalGap1, horizontalGap2, verticalGap1, verticalGap2);

    }

    /**
     * adds walls with 4 gaps in total to the world, dividing it in 4 equal sized areas
     * the gaps allow the robot to switch from area to area
     *
     * @param horizontalGap1
     * @param horizontalGap2
     * @param verticalGap1
     * @param verticalGap2
     */
    private void putWalls(int horizontalGap1, int horizontalGap2, int verticalGap1, int verticalGap2){
        for (int i = 0; i < WORLD_SIZE; i++) {
            if (i != horizontalGap1 && i != horizontalGap2) {
                placeHorizontalWall(i, WORLD_SIZE / 2 - 1);
            }
            if (i != verticalGap1 && i != verticalGap2) {
                placeVerticalWall(WORLD_SIZE / 2 - 1, i);
            }
        }
    }
}
