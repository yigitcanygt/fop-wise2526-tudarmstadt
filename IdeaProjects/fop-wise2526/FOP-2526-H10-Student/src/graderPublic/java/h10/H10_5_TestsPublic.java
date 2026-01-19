package h10;

import h10.animals.Animal;
import h10.animals.birds.Penguin;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.declaration.CtMethod;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class H10_5_TestsPublic {

    @Test
    public void testTestAnyMatchTwoLionsPublic() {
        CtMethod ctMethod =
            ((BasicMethodLink) BasicTypeLink.of(EnclosureTest.class).getMethod(identical("testAnyMatch"))).getCtElement();

        String body = ctMethod.getBody().toStringDebug();

        long lions = body.lines()
            .filter(line -> line.contains("new h10.animals.mammals.Lion"))
            .count();

        long jugLions = body.lines()
            .filter(line -> line.contains("new h10.animals.mammals.JugglingLion"))
            .count();

        boolean hasParameterEnclosure = body.contains("GroundEnclosure<h10.animals.mammals.Lion>");

        assertTrue(lions > 1, emptyContext(), r -> "The amount of created Lions does not match expected Amount. Found " + lions);
        assertTrue(jugLions > 0, emptyContext(), r -> "The amount of created Lions does not match expected Amount. Found " + lions);
        assertTrue(hasParameterEnclosure, emptyContext(), r -> "Could not find correctly typed Enclosure in test.");
    }

    @Test
    public void testTestMapFunctionalPenguinPublic() {
        // Track number of mapFunctional calls
        AtomicInteger mapCalls = new AtomicInteger();

        WaterEnclosure<Penguin> nursery = spy(new WaterEnclosure<>());

        nursery.getAnimals().push(new Penguin("Pip", 2));
        nursery.getAnimals().push(new Penguin("Penny", 3));
        nursery.getAnimals().push(new Penguin("Percy", 1));

        doAnswer(inv -> {
            mapCalls.incrementAndGet();
            return spy(inv.callRealMethod());
        }).when(nursery).mapFunctional(any(), any());

        WaterEnclosure<Penguin> adult =
            (WaterEnclosure<Penguin>) nursery.mapFunctional(() -> new GroundEnclosure<>(),
                    p -> new Penguin("Sir " + ((Penguin) p).getName(), ((Penguin) p).getAge() + 1))
                .mapFunctional(() -> new WaterEnclosure<>(), a -> a);

        // mapFunctional is used
        assertEquals(1, mapCalls.get(), emptyContext(), r -> "mapFunctional operations were not performed correctly");

        // animals have aged & been renamed
        StackOfObjects<Penguin> animals = adult.getAnimals();
        assertTrue(((Animal) animals.get(0)).getName().contains("Sir"), emptyContext(), r -> "The name is not changed correctly.");
        assertEquals(3, ((Animal) animals.get(0)).getAge(), emptyContext(), r -> "The penguins haven't aged.");

        assertTrue(((Animal) animals.get(1)).getName().contains("Sir"), emptyContext(), r -> "The name is not changed correctly.");
        assertEquals(4, ((Animal) animals.get(1)).getAge(), emptyContext(), r -> "The penguins haven't aged.");

        assertTrue(((Animal) animals.get(2)).getName().contains("Sir"), emptyContext(), r -> "The name is not changed correctly.");
        assertEquals(2, ((Animal) animals.get(2)).getAge(), emptyContext(), r -> "The penguins haven't aged.");
    }

}
