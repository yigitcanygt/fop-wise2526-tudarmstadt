package h10.animals;

/**
 * An object of a class {@link Animal} represents an animal.
 */
public abstract class Animal {
    /**
     * Name of the animal.
     */
    protected String name;
    /**
     * Age of the animal.
     */
    private int age;

    /**
     * Preferred feeding location by the animal
     */
    private final Location feedingLocation;

    /**
     * Current location of the animal
     */
    private Location currentLocation;

    /**
     * A 'true' value represents that the animal is hungry.
     */
    private boolean isHungry = true;

    /**
     * Constructs an Animal with the given name, age, preferred feeding location and initial location.
     *
     * @param name            the name of the animal
     * @param age             the age of the animal
     * @param feedingLocation the preferred feeding location of the animal
     * @param currentLocation the initial location of the animal
     */
    public Animal(String name, int age, Location feedingLocation, Location currentLocation) {
        this.name = name;
        this.age = age;
        this.feedingLocation = feedingLocation;
        this.currentLocation = currentLocation;
    }

    /**
     * @param currentLocation the new location of the animal
     */
    protected void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * @return current location of the animal
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @return the name of the animal
     */
    public String getName() {
        return name;
    }

    /**
     * Lets the animal eat. This prints a message to the system output noting that the animal has eaten and sets the
     * animal to not hungry.
     */
    public void eat() {
        System.out.println(name + " ate...");
        isHungry = false;
    }

    /**
     * Lets the animal sleep. This prints a message to the system output noting that the animal has eaten. It also
     * increases the age of the animal and sets the animal to hungry.
     */
    public void sleep() {
        System.out.println(name + " slept...");
        isHungry = true;
        age++;
    }

    /**
     * @return true if the animal is hungry; false otherwise
     */
    public boolean isHungry() {
        return isHungry;
    }

    /**
     * @return the age of the animal
     */
    public int getAge() {
        return age;
    }

    /**
     * @return preferred feeding location
     */
    public Location getFeedingLocation() {
        return feedingLocation;
    }
}
