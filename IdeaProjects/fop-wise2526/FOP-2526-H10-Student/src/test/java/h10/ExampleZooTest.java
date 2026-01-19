package h10;

import h10.animals.birds.Penguin;
import h10.animals.fish.Fish;
import org.junit.jupiter.api.Test;

public class ExampleZooTest {

    @Test
    public void runExampleZoo() {
        System.out.println("\nCreating fish enclosure...");
        WaterEnclosure<Fish> fishEnclosure = new WaterEnclosure<>();
        fishEnclosure.getAnimals().push(new Fish("Fishaaa", 1));
        fishEnclosure.getAnimals().push(new Fish("Fishbb", 2));
        fishEnclosure.getAnimals().push(new Fish("Fisch", 3));

        // can be used after H10.3.1
//        System.out.println("Checking fish conditions with anyMatch...");
//        System.out.println("Any fish at surface level (elevation >= 0)? " +
//            fishEnclosure.anyMatch(fish -> fish.getElevation() >= 0));
//
//        System.out.println("Any fish named 'Fishaaa'? " +
//            fishEnclosure.anyMatch(fish -> fish.getName().equals("Fishaaa")));
//
//        System.out.println("Any fish older than 2? " +
//            fishEnclosure.anyMatch(fish -> fish.getAge() > 2));

        System.out.println("\nFeed the fish...");
        fishEnclosure.feed();

        System.out.println("\nGet fish mean elevation...");
        System.out.println("Mean elevation: " + fishEnclosure.getMeanElevation());

        System.out.println("\nCreating penguin enclosure...");
        GroundEnclosure<Penguin> penguinGroundEnclosure = new GroundEnclosure<>();
        penguinGroundEnclosure.getAnimals().push(new Penguin("Penga", 5));
        penguinGroundEnclosure.getAnimals().push(new Penguin("Pengb", 10));
        penguinGroundEnclosure.getAnimals().push(new Penguin("Pengc", 10));
        penguinGroundEnclosure.getAnimals().push(new Penguin("Pengd", 30));
        penguinGroundEnclosure.getAnimals().push(new Penguin("Penge", 30));

        System.out.println("\nMigrating the Penguins to a WaterEnclosure...");
        WaterEnclosure<Penguin> penguinWaterEnclosure = new WaterEnclosure<>();
//        while (penguinGroundEnclosure.getAnimals().size() > 0) {
//            Penguin penguin = penguinGroundEnclosure.getAnimals().pop();
//            penguinWaterEnclosure.getAnimals().push(penguin);
//        }


        // can be used after H10.3.3
//        System.out.println("\nCalculating total penguin age using reduce...");
//        int totalAge = penguinWaterEnclosure.reduce(0, (sum, penguin) -> sum + penguin.getAge());
//        System.out.println("Total age of all penguins: " + totalAge);
//
//        System.out.println("\nFinding oldest penguin using reduce..."); // reduce can also replace a simple find operation
//        Penguin oldestPenguin = penguinWaterEnclosure.reduce(
//            new Penguin("", 0),
//            (oldest, current) -> current.getAge() > oldest.getAge() ? current : oldest
//        );
//        System.out.println("Oldest penguin: " + oldestPenguin.getName() + " (age " + oldestPenguin.getAge() + ")");
    }
}
