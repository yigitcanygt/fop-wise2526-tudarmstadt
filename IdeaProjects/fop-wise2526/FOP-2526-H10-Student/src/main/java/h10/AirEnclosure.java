package h10;
import h10.animals.Location;
import h10.abilities.Flies;
import h10.animals.Animal;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class AirEnclosure<A extends Animal & Flies> implements Enclosure<A> {

    @StudentImplementationRequired("H10.2.2")
    final StackOfObjects<A> animals = new StackOfObjects<>();

    @StudentImplementationRequired("H10.2.2")
    @Override
    public StackOfObjects<A> getAnimals() {
        return animals;
    }

    @StudentImplementationRequired("H10.2.2")
    @Override
    public void feed() {
        for (int idx = 0; idx < animals.size(); idx++) {
            A animal = animals.get(idx);
            if (animal.isHungry()) {
                Location feedingLocation = animal.getFeedingLocation();
                Location currentLocation = animal.getCurrentLocation();
                if (currentLocation != feedingLocation) {
                    animal.flyTo(feedingLocation);
                }
                animal.eat();
            }
        }
    }
}
