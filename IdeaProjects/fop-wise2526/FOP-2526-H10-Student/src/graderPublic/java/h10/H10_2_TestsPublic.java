package h10;

import h10.abilities.Flies;
import h10.animals.Animal;
import h10.animals.birds.Parrot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static h10.H10_TestUtilsPublic.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class H10_2_TestsPublic {
    BasicTypeLink enclosureLink;
    Method getAnimals;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUpPublic() {
        enclosureLink = BasicTypeLink.of(Enclosure.class);

        getAnimals = enclosureLink.getMethod(identical("getAnimals")).reflection();
    }

    @Test
    public void testEnclosureClassParameterPublic() {
        assertDefinedParameters(Enclosure.class, Set.of(matchUpperBounds("A", Animal.class)));
    }

    @Test
    public void testGetAnimalsParameterPublic() {
        Predicate<Type> typeMatcher =
            getDefinedTypes(Enclosure.class, ".*").stream()
                .map(H10_TestUtilsPublic::match)
                .reduce(Predicate::or)
                .orElse(new H10_TestUtilsPublic.GenericPredicate(i -> false, "Expected type is not defined"));

        assertReturnParameter(getAnimals, matchNested(StackOfObjects.class, typeMatcher));
    }

    @Test
    public void testAirEnclosureClassParameterPublic() {
        assertDefinedParameters(AirEnclosure.class, Set.of(matchUpperBounds("A", Animal.class, Flies.class)));
    }

    @Test
    public void testFeedPublic() {

        Enclosure enclosure = (Enclosure) new AirEnclosure();
        List<Parrot> animals = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Parrot mock = mock(Parrot.class, CALLS_REAL_METHODS);
            ReflectionUtilsPublic.setFieldValue(mock, "isHungry", false);
            if (i % 2 == 0) {
                ReflectionUtilsPublic.setFieldValue(mock, "isHungry", true);
            }

            enclosure.getAnimals().push(mock);
            animals.add(mock);
        }

        Context context = contextBuilder()
            .add("Animals", animals)
            .build();

        enclosure.feed();

        for (int i = 0; i < 10; i++) {
            if (enclosure.getAnimals().size() <= 0) {
                fail(context, r -> "AirEnclosure does not have correct number of Animals after feeding.");
            }
            Parrot mock = (Parrot) enclosure.getAnimals().pop();

            int id = animals.indexOf(mock);

            if (id % 2 == 0) {
                verify(mock).eat();
            } else {
                verify(mock, never()).eat();
            }
        }
    }

}
