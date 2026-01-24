package h11;

import h11.mocking.ReflectionUtilsP;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class RandomListRecursiveTestPublic extends H11_TestP {

    @ParameterizedTest
    @MethodSource("provideTestGet_first")
    public void testGet_first(List<Object> invoked, int index, int randomIndex, Object expectedElement,
                              List<Object> expectedList) {
        RandomListRecursive<Object> test = mock(RandomListRecursive.class, CALLS_REAL_METHODS);
        RandomListRecursive<Object> dataProvider = new RandomListRecursive<>(invoked.toArray(), null);

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        test.head = dataProvider.head;
        ReflectionUtilsP.setFieldValue(test, "size", dataProvider.size());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);
        assertEquals(expectedElement, actualElement, context, r -> "Element returned by get does not match expected!");

        assertEquals(expectedList, toList(test), context, r -> "List does not match expected!");
    }

    @ParameterizedTest
    @MethodSource("provideTestGet_neighbor")
    public void testGet_neighbor(List<Object> invoked, int index, int randomIndex, Object expectedElement,
                                 List<Object> expectedList) {
        RandomListRecursive<Object> test = mock(RandomListRecursive.class, CALLS_REAL_METHODS);
        RandomListRecursive<Object> dataProvider = new RandomListRecursive<>(invoked.toArray(), null);

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        test.head = dataProvider.head;
        ReflectionUtilsP.setFieldValue(test, "size", dataProvider.size());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);
        assertEquals(expectedElement, actualElement, context, r -> "Element returned by get does not match expected!");

        assertEquals(expectedList, toList(test), context, r -> "List does not match expected!");
    }

    @ParameterizedTest
    @MethodSource("provideTestGet_non_neighbor")
    public void testGet_non_neighbor(List<Object> invoked, int index, int randomIndex, Object expectedElement,
                                     List<Object> expectedList) {
        RandomListRecursive<Object> test = mock(RandomListRecursive.class, CALLS_REAL_METHODS);
        RandomListRecursive<Object> dataProvider = new RandomListRecursive<>(invoked.toArray(), null);

        doReturn(randomIndex).when(test).getRandomIndex(anyInt());

        test.head = dataProvider.head;
        ReflectionUtilsP.setFieldValue(test, "size", dataProvider.size());

        Context context = contextBuilder()
            .add("invoked", invoked)
            .add("index", index)
            .add("expectedElement", expectedElement)
            .add("expectedList", expectedList)
            .build();

        Object actualElement = test.get(index);
        assertEquals(expectedElement, actualElement, context, r -> "Element returned by get does not match expected!");

        assertEquals(expectedList, toList(test), context, r -> "List does not match expected!");
    }

    public static Stream<Arguments> provideTestGet_non_neighbor() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                1,
                4,
                List.of(1, 4, 3, 2, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                4,
                0,
                2,
                List.of(2, 5, 4, 3, 6, 1)
            )
        );
    }

    public static Stream<Arguments> provideTestGet_first() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                0,
                0,
                1,
                List.of(1, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                0,
                0,
                6,
                List.of(6, 5, 4, 3, 2, 1)
            ),
            Arguments.of(
                List.of(1),
                0,
                0,
                1,
                List.of(1)
            )
        );
    }

    public static Stream<Arguments> provideTestGet_neighbor() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                5,
                4,
                6,
                List.of(1, 2, 3, 4, 6, 5)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                2,
                4,
                List.of(1, 2, 4, 3, 5, 6)
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                2,
                1,
                3,
                List.of(1, 3, 2, 4, 5, 6)
            )
        );

    }
}
