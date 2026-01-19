package h10;

import h10.abilities.Flies;
import h10.abilities.Walks;
import h10.animals.Animal;
import h10.animals.birds.Parrot;
import h10.animals.birds.Penguin;
import h10.animals.mammals.Lion;
import h10.animals.reptiles.Turtle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static h10.H10_TestUtilsPublic.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class H10_4_TestsPublic {

    BasicTypeLink stackLink;
    Field hasTwoLegs;
    Method flyAndEat;

    @BeforeEach
    public void setUpPublic() {
        stackLink = BasicTypeLink.of(Enclosure.class);

        hasTwoLegs = stackLink.getField(identical("HAS_2_LEGS")).reflection();
        flyAndEat = stackLink.getMethod(identical("flyAndEat")).reflection();

    }

    @Test
    public void testHAS_2_LEGS_TypePublic() {
        assertType(hasTwoLegs, matchNested(Predicate.class, match(Walks.class)));
    }

    @Test
    public void testHAS_2_LEGS_ImplementationPublic() {
        assertNotNull(Enclosure.HAS_2_LEGS, emptyContext(), r -> "HAS_2_LEGS is not implemented");

        try {
            assertTrue(Enclosure.HAS_2_LEGS.test(new Penguin("Rico", 42)),
                emptyContext(),
                r -> "HAS_2_LEGS should return true for a penguin.");

            assertFalse(Enclosure.HAS_2_LEGS.test(new Lion("Simba", 69)),
                emptyContext(),
                r -> "HAS_2_LEGS should return false for a lion.");

            assertFalse(Enclosure.HAS_2_LEGS.test(new Turtle("Oogway", 65521)),
                emptyContext(),
                r -> "HAS_2_LEGS should return true for a turtle.");

        } catch (ClassCastException exception) {
            fail(
                emptyContext(),
                r -> "HAS_2_LEGS does not accept correct type of objects. Message of thrown Exception: %s".formatted(
                    exception.getMessage())
            );
        }
    }

    @Test
    public void testFlyAndEatDefinedTypePublic() {
        Method flyAndEat = Arrays.stream(Enclosure.class.getDeclaredMethods())
            .filter(m -> m.getName().equals("flyAndEat") && m.getParameterCount() == 0)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Method flyAndEat() without parameters not found"));

        assertDefinedParameters(flyAndEat, Set.of(matchUpperBounds("F", Animal.class, Flies.class)));
    }

    @Test
    public void testFlyAndEatImplementationPublic() {
        Consumer consumer = Enclosure.flyAndEat();
        assertNotNull(consumer, emptyContext(), r -> "FlyAndEat is not implemented");
        try {
            Parrot mock = mock(Parrot.class);

            consumer.accept(mock);

            verify(mock, atLeastOnce()).flyTo(mock.getFeedingLocation());
            verify(mock, atLeastOnce()).eat();

        } catch (ClassCastException exception) {
            fail(
                emptyContext(),
                r -> "flyAndEat does not accept correct type of objects. Message of thrown Exception: %s".formatted(exception.getMessage())
            );
        }
    }

    @Test
    public void testFlyAndEatReturnTypePublic() {
        Method flyAndEat = Arrays.stream(Enclosure.class.getDeclaredMethods())
            .filter(m -> m.getName().equals("flyAndEat") && m.getParameterCount() == 0)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Method flyAndEat() without parameters not found"));

        Predicate<Type> methodTypeMatcher = getDefinedTypes(flyAndEat, ".*").stream()
            .map(H10_TestUtilsPublic::match)
            .reduce(Predicate::or)
            .orElse(new H10_TestUtilsPublic.GenericPredicate(i -> false, "Expected type is not defined"));

        assertReturnParameter(flyAndEat, matchNested(Consumer.class, methodTypeMatcher));
    }


}
