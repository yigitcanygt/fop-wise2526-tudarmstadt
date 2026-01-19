package h10.animals.fish;

public class ClownFish extends Fish {
    public ClownFish(String name, int age) {
        super(name, age);
    }

    /**
     * Prints a joke about fish playing basketball.
     */
    public void tellJoke() {
        System.out.println(getName() + " says: Why don't fish play basketball? Because they're afraid of the net!");
    }
}
