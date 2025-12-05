package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A fuel powered chainsaw
 */
public class Chainsaw implements FuelPowered {

    @DoNotTouch
    private boolean motorRunning;
    @DoNotTouch
    private int attempts;
    @DoNotTouch
    private Wood[] remainingWood;

    /**
     * Creates a new Chainsaw
     */
    @DoNotTouch
    public Chainsaw() {
        this.motorRunning = false;
        this.attempts = 0;
        this.remainingWood = new Wood[0];
    }

    /**
     * attempts to start the chainsaw.
     * guaranteed to succeed after 3 attempts.
     */
    @DoNotTouch
    public void attemptStart() {
        if (motorRunning) {
            return;
        }
        if (attempts >= 3 || Math.random() > 0.5) {
            motorRunning = true;
        }
        attempts += 1;
    }

    /**
     * stops the chainsaw
     */
    @DoNotTouch
    public void stopMotor() {
        motorRunning = false;
        attempts = 0;
    }

    /**
     * checks is the motor is running
     *
     * @return true if the motor is running
     */
    @DoNotTouch
    public boolean isMotorRunning() {
        return motorRunning;
    }

    /**
     * adds wood to be sawed by the chainsaw
     *
     * @param wood the piece of wood to be added
     */
    @DoNotTouch
    public void addWood(Wood wood) {
        Wood[] newWood = new Wood[remainingWood.length + 1];
        for (int i = 0; i < remainingWood.length; i++) {
            newWood[i] = remainingWood[i];
        }
        newWood[newWood.length - 1] = wood;
        remainingWood = newWood;
    }
    @Override
        public FuelType getFuelType() {
        return FuelType.GASOLINE;
    }
    /**
     * saws the oldest remaining piece of wood
     *
     * @param duration the duration to be sawed
     */
    @StudentImplementationRequired("H6.4.1")
    public void sawWood(int duration) {
        if (motorRunning == false) {
            return;
        }

        if (remainingWood.length == 0) {
            return;
        }

        Wood currentWood = remainingWood[0];
        double cuttingPower = duration / currentWood.strength;
        currentWood.cuttingDepth = currentWood.cuttingDepth - cuttingPower;

        if (currentWood.cuttingDepth <= 0) {
            Wood[] yeniDizi = new Wood[remainingWood.length - 1];
            int index = 0;
            while (index < yeniDizi.length) {
                yeniDizi[index] = remainingWood[index + 1];
                index = index + 1;
            }
            remainingWood = yeniDizi;
        }
    }
    /**
     * @return the number of remaining wood logs to be sawed
     */
    @DoNotTouch
    public int getRemainingWood() {
        return remainingWood.length;
    }
}
