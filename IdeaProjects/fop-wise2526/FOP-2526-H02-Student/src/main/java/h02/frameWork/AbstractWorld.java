package h02.frameWork;

import fopbot.ColorProfile;
import fopbot.World;

import java.awt.*;

import static h02.frameWork.RandomNumberGenerator.getRandomNumber;

public abstract class AbstractWorld extends World {
    protected final int WORLD_SIZE;

    /**
     * Constructs a square world
     *
     * @param worldSize defines the size of the world
     */
    public AbstractWorld(int worldSize){
        this.WORLD_SIZE = worldSize;
        setSize(WORLD_SIZE, WORLD_SIZE);
    }

    /**
     * starts the world with basic characteristics
     */
    public void start(){
        setupWorld();
        setDestinationField();
        setCoinColor();
        setVisible(true);
        dynamicWindow();
    }

    /**
     * adds world-specific characteristics
     */
    protected abstract void setupWorld();

    /**
     * defines the color of the destination field
     */
    protected abstract void setDestinationField();

    /**
     * returns a colorblind-friendly color for destination fields
     * @return color
     */
    protected Color getDestinationFieldColor() {
        return new Color(167, 199, 51);
    }

    /**
     * returns random starting coordinates for the robot
     * @return coordinates as a Point
     */
    protected Point getRandomStartCoordinates(){
        return new Point(getRandomNumber(WORLD_SIZE), getRandomNumber(WORLD_SIZE));
    }

    /**
     * changes the coin color to a color-blind-friendly color
     */
    private void setCoinColor(){
        Color coinColor = new Color(223, 104, 177);
        getGlobalWorld().setColorProfile(ColorProfile.DEFAULT.toBuilder()
            .coinColorLight(coinColor)
            .coinColorDark(coinColor)
            .build());
    }

    /**
     * adjusts the world to the screen
     * otherwise windows-taskbar overlaps with the world
     */
    private void dynamicWindow(){
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int environmentHeight = environment.getMaximumWindowBounds().height;
        int environmentWidth = environment.getMaximumWindowBounds().width;
        getGlobalWorld().getGuiFrame().setSize(environmentWidth / 2, environmentHeight);
    }
}
