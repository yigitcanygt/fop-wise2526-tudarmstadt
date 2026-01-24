package h11;

import h11.mocking.ReflectionUtilsP;
import h11.mocking.StudentImplementationException;
import kotlin.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.stubbing.Answer;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.fail;

@TestForSubmission
public class BidirectionalListIteratorTest extends H11_TestP {

    @ParameterizedTest
    @MethodSource("provideTestHasPrevious")
    public void testHasPrevious(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                                ListItem<Object> cursorExpected, boolean expected) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = toTest.getThird();

        doAnswer(CALLS_REAL_METHODS).when(iter).hasPrevious();

        boolean actual = iter.hasPrevious();

        assertEquals(expected, actual, emptyContext(), r -> "The returned value does not match expected.");
        testState(lastReturnedExpected, cursorExpected, previousesExpected, elements, list, iter);
    }


    public static Stream<Arguments> provideTestHasPrevious() {
        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                4,
                new ListItem<Object>(3),
                new ListItem<Object>(4),
                true
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                0,
                null,
                new ListItem<Object>(0),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                2,
                new ListItem<Object>(5),
                new ListItem<Object>(4),
                true
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                5,
                new ListItem<Object>(2),
                new ListItem<Object>(1),
                true
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestPrevious")
    public void testPrevious(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                             ListItem<Object> cursorExpected, ListItem<ListItem<Object>> previousesExpected, Object expected,
                             boolean throwing) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();

        doAnswer(CALLS_REAL_METHODS).when(iter).previous();

        Object actual;
        if (throwing) {
            try {
                ReflectionUtilsP.callMethod(iter, BidirectionalIterator.class.getDeclaredMethod("previous"));
                fail(emptyContext(), r -> "No exception was thrown when one was expected.");
            } catch (Exception e) {
                if (e instanceof StudentImplementationException sie && !(sie.getCause() instanceof NoSuchElementException)) {
                    fail(sie.getContext(), r -> sie.getMessage());
                }
            }
            actual = null;
        } else {
            actual = iter.previous();
        }

        assertEquals(expected, actual, emptyContext(), r -> "The returned value does not match expected.");
        testState(lastReturnedExpected, cursorExpected, previousesExpected, elements, list, iter);
    }

    public static Stream<Arguments> provideTestPrevious() {
        ListItem<ListItem<Object>> previousses0 = new ListItem<>(new ListItem<>(2));
        previousses0.next = new ListItem<>(new ListItem<>(1));
        previousses0.next.next = new ListItem<>(new ListItem<>(0));

        ListItem<ListItem<Object>> previousses2 = new ListItem<>(new ListItem<>(6));

        ListItem<ListItem<Object>> previousses3 = new ListItem<>(new ListItem<>(3));
        previousses3.next = new ListItem<>(new ListItem<>(4));
        previousses3.next.next = new ListItem<>(new ListItem<>(5));
        previousses3.next.next.next = new ListItem<>(new ListItem<>(6));

        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                4,
                new ListItem<Object>(3),
                new ListItem<Object>(3),
                previousses0,
                3,
                false
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                0,
                null,
                new ListItem<Object>(0),
                null,
                null,
                true
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                2,
                new ListItem<Object>(5),
                new ListItem<Object>(5),
                previousses2,
                5,
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                5,
                new ListItem<Object>(2),
                new ListItem<Object>(2),
                previousses3,
                2,
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestHasNext")
    public void testHasNext(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                            ListItem<Object> cursorExpected, boolean expected) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = toTest.getThird();

        doAnswer(CALLS_REAL_METHODS).when(iter).hasNext();

        boolean actual = iter.hasNext();

        assertEquals(expected, actual, emptyContext(), r -> "The returned value does not match expected.");
        testState(lastReturnedExpected, cursorExpected, previousesExpected, elements, list, iter);
    }

    public static Stream<Arguments> provideTestHasNext() {
        return Stream.of(
            Arguments.of(
                List.of(421, 466, 550, 690, 705, 720, 759),
                4,
                new ListItem<Object>(690),
                new ListItem<Object>(705),
                true
            ), Arguments.of(
                List.of(6969, 555, 707, 722, 755, 808, 869),
                0,
                null,
                new ListItem<Object>(6969),
                true
            ), Arguments.of(
                List.of(69, 42, 55, 70, 72, 75, 80, 89),
                2,
                new ListItem<Object>(42),
                new ListItem<Object>(55),
                true
            ), Arguments.of(
                List.of(66, 55, 44, 33, 22, 11),
                6,
                new ListItem<Object>(11),
                null,
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestNext")
    public void testNext(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                         ListItem<Object> cursorExpected, ListItem<ListItem<Object>> previousesExpected, Object expected) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();

        doAnswer(CALLS_REAL_METHODS).when(iter).next();

        Object actual = iter.next();

        assertEquals(expected, actual, emptyContext(), r -> "The returned value does not match expected.");
        testState(lastReturnedExpected, cursorExpected, previousesExpected, elements, list, iter);
    }

    public static Stream<Arguments> provideTestNext() {
        ListItem<ListItem<Object>> previousses0 = new ListItem<>(new ListItem<>(70));
        previousses0.next = new ListItem<>(new ListItem<>(69));
        previousses0.next.next = new ListItem<>(new ListItem<>(55));
        previousses0.next.next.next = new ListItem<>(new ListItem<>(46));
        previousses0.next.next.next.next = new ListItem<>(new ListItem<>(42));

        ListItem<ListItem<Object>> previousses1 = new ListItem<>(new ListItem<>(69));

        ListItem<ListItem<Object>> previousses2 = new ListItem<>(new ListItem<>(55));
        previousses2.next = new ListItem<>(new ListItem<>(42));
        previousses2.next.next = new ListItem<>(new ListItem<>(69));

        ListItem<ListItem<Object>> previousses3 = new ListItem<>(new ListItem<>(11));
        previousses3.next = new ListItem<>(new ListItem<>(22));
        previousses3.next.next = new ListItem<>(new ListItem<>(33));
        previousses3.next.next.next = new ListItem<>(new ListItem<>(44));
        previousses3.next.next.next.next = new ListItem<>(new ListItem<>(55));
        previousses3.next.next.next.next.next = new ListItem<>(new ListItem<>(66));


        return Stream.of(
            Arguments.of(
                List.of(42, 46, 55, 69, 70, 72, 75),
                4,
                new ListItem<Object>(70),
                new ListItem<Object>(72),
                previousses0,
                70
            ), Arguments.of(
                List.of(69, 55, 70, 72, 75, 80, 89),
                0,
                new ListItem<Object>(69),
                new ListItem<Object>(55),
                previousses1,
                69
            ), Arguments.of(
                List.of(69, 42, 55, 70, 72, 75, 80, 89),
                2,
                new ListItem<Object>(55),
                new ListItem<Object>(70),
                previousses2,
                55
            ), Arguments.of(
                List.of(66, 55, 44, 33, 22, 11),
                5,
                new ListItem<Object>(11),
                null,
                previousses3,
                11
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestAdd")
    public void testAdd(List<Object> elements, int curserPos, Object toAdd, ListItem<Object> lastReturnedExpected,
                               ListItem<Object> cursorExpected,
                               List<Object> expectedList) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = new ListItem<>(new ListItem<>(toAdd));
        previousesExpected.next = toTest.getThird();

        doAnswer(CALLS_REAL_METHODS).when(iter).add(any());

        iter.add(toAdd);

        testState(lastReturnedExpected, cursorExpected, previousesExpected, expectedList, list, iter);
        assertEquals(elements.size() + 1, list.size(), emptyContext(), r -> "The list does not have the correct size.");
    }

    public static Stream<Arguments> provideTestAdd() {
        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                0,
                100,
                null,
                new ListItem<Object>(0),
                List.of(100, 0, 1, 2, 3, 4, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                6,
                100,
                null,
                null,
                List.of(6, 5, 4, 3, 2, 1, 100)
            ),
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                4,
                100,
                null,
                new ListItem<Object>(4),
                List.of(0, 1, 2, 3, 100, 4, 5, 6)
            ),
            Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                2,
                100,
                null,
                new ListItem<Object>(4),
                List.of(6, 5, 100, 4, 3, 2, 1)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestAdd_empty")
    public void testAdd_empty(List<Object> elements, int curserPos, Object toAdd, ListItem<Object> lastReturnedExpected,
                        ListItem<Object> cursorExpected,
                        List<Object> expectedList) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = new ListItem<>(new ListItem<>(toAdd));
        previousesExpected.next = toTest.getThird();

        doAnswer(CALLS_REAL_METHODS).when(iter).add(any());

        iter.add(toAdd);

        testState(lastReturnedExpected, cursorExpected, previousesExpected, expectedList, list, iter);
        assertEquals(elements.size() + 1, list.size(), emptyContext(), r -> "The list does not have the correct size.");
    }

    public static Stream<Arguments> provideTestAdd_empty() {
        return Stream.of(
            Arguments.of(
                List.of(),
                0,
                100,
                null,
                null,
                List.of(100)
            ),
            Arguments.of(
                List.of(),
                0,
                1,
                null,
                null,
                List.of(1)
            ),
            Arguments.of(
                List.of(),
                0,
                5,
                null,
                null,
                List.of(5)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestRemove_next")
    public void testRemove_next(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                                ListItem<Object> cursorExpected,
                                List<Object> expectedList, boolean throwing) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = toTest.getThird();
        if (previousesExpected != null) {
            previousesExpected = previousesExpected.next;
        }

        doAnswer(CALLS_REAL_METHODS).when(iter).remove();

        if (throwing) {
            try {
                ReflectionUtilsP.callMethod(iter, BidirectionalIterator.class.getDeclaredMethod("remove"));
                fail(emptyContext(), r -> "No exception was thrown when one was expected.");
            } catch (Exception e) {
                if (e instanceof StudentImplementationException sie && !(sie.getCause() instanceof IllegalStateException)) {
                    fail(sie.getContext(), r -> sie.getMessage());
                }
            }
        } else {
            iter.remove();
        }

        testState(lastReturnedExpected, cursorExpected, previousesExpected, expectedList, list, iter);
        assertEquals(
            elements.size() - (throwing ? 0 : 1),
            list.size(),
            emptyContext(),
            r -> "The list does not have the correct size."
        );
    }

    public static Stream<Arguments> provideTestRemove_next() {
        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                4,
                null,
                new ListItem<Object>(4),
                List.of(0, 1, 2, 4, 5, 6),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                2,
                null,
                new ListItem<Object>(4),
                List.of(6, 4, 3, 2, 1),
                false
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4),
                5,
                null,
                null,
                List.of(0, 1, 2, 3),
                false
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                1,
                null,
                new ListItem<Object>(1),
                List.of(1, 2, 3, 4, 5, 6),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                1,
                null,
                new ListItem<Object>(5),
                List.of(5, 4, 3, 2, 1),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                6,
                null,
                null,
                List.of(6, 5, 4, 3, 2),
                false
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                6,
                null,
                null,
                List.of(1, 2, 3, 4, 5),
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestRemove_exception")
    public void testRemove_exception(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                                     ListItem<Object> cursorExpected,
                                     List<Object> expectedList, boolean throwing) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = toTest.getThird();

        ReflectionUtilsP.setFieldValue(iter, "lastReturned", null);

        doAnswer(CALLS_REAL_METHODS).when(iter).remove();

        if (throwing) {
            try {
                ReflectionUtilsP.callMethod(iter, BidirectionalIterator.class.getDeclaredMethod("remove"));
                fail(emptyContext(), r -> "No exception was thrown when one was expected.");
            } catch (Exception e) {
                if (e instanceof StudentImplementationException sie && !(sie.getCause() instanceof IllegalStateException)) {
                    fail(sie.getContext(), r -> sie.getMessage());
                }
            }
        } else {
            iter.remove();
        }

        testState(lastReturnedExpected, cursorExpected, previousesExpected, expectedList, list, iter);
        assertEquals(
            elements.size() - (throwing ? 0 : 1),
            list.size(),
            emptyContext(),
            r -> "The list does not have the correct size."
        );
    }

    public static Stream<Arguments> provideTestRemove_exception() {
        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4),
                0,
                null,
                new ListItem<>(0),
                List.of(0, 1, 2, 3, 4),
                true
            ), Arguments.of(
                List.of(),
                0,
                null,
                null,
                List.of(),
                true
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                4,
                null,
                new ListItem<>(2),
                List.of(6, 5, 4, 3, 2, 1),
                true
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                3,
                null,
                new ListItem<>(4),
                List.of(1, 2, 3, 4, 5, 6),
                true
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestRemove_previous")
    public void testRemove_previous(List<Object> elements, int curserPos, ListItem<Object> lastReturnedExpected,
                           ListItem<Object> cursorExpected,
                           List<Object> expectedList, boolean throwing) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator_afterPrev(elements, curserPos);
        AbstractSelfOrganizingList<Object> list = (AbstractSelfOrganizingList<Object>) toTest.getFirst();
        BidirectionalIterator<Object> iter = toTest.getSecond();
        ListItem<ListItem<Object>> previousesExpected = toTest.getThird();
        if (previousesExpected != null) {
            previousesExpected = previousesExpected.next;
        }

        doAnswer(CALLS_REAL_METHODS).when(iter).remove();

        if (throwing) {
            try {
                ReflectionUtilsP.callMethod(iter, BidirectionalIterator.class.getDeclaredMethod("remove"));
                fail(emptyContext(), r -> "No exception was thrown when one was expected.");
            } catch (Exception e) {
                if (e instanceof StudentImplementationException sie && !(sie.getCause() instanceof IllegalStateException)) {
                    fail(sie.getContext(), r -> sie.getMessage());
                }
            }
        } else {
            iter.remove();
        }

        testState(lastReturnedExpected, cursorExpected, previousesExpected, expectedList, list, iter);
        assertEquals(
            elements.size() - (throwing ? 0 : 1),
            list.size(),
            emptyContext(),
            r -> "The list does not have the correct size."
        );
    }

    public static Stream<Arguments> provideTestRemove_previous() {
        return Stream.of(
            Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                4,
                null,
                new ListItem<Object>(4),
                List.of(0, 1, 2, 4, 5, 6),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                2,
                null,
                new ListItem<Object>(4),
                List.of(6, 4, 3, 2, 1),
                false
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4),
                5,
                null,
                null,
                List.of(0, 1, 2, 3),
                false
            ), Arguments.of(
                List.of(0, 1, 2, 3, 4, 5, 6),
                1,
                null,
                new ListItem<Object>(1),
                List.of(1, 2, 3, 4, 5, 6),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                1,
                null,
                new ListItem<Object>(5),
                List.of(5, 4, 3, 2, 1),
                false
            ), Arguments.of(
                List.of(6, 5, 4, 3, 2, 1),
                6,
                null,
                null,
                List.of(6, 5, 4, 3, 2),
                false
            ),
            Arguments.of(
                List.of(1, 2, 3, 4, 5, 6),
                6,
                null,
                null,
                List.of(1, 2, 3, 4, 5),
                false
            )
        );
    }

    private static Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> setupIterator_afterPrev(
        List<Object> elements, int curserPos) {

        Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> toTest =
            setupIterator(elements, curserPos);

        BidirectionalListIterator<Object> iter = (BidirectionalListIterator<Object>) toTest.component2();

        iter.cursor = iter.lastReturned;
        while (iter.previouses != null && iter.previouses.key != iter.cursor){
            iter.previouses = iter.previouses.next;
        }
        iter.previouses = iter.previouses.next;

        return toTest;
    }

    private static Triple<SelfOrganizingList<Object>, BidirectionalIterator<Object>, ListItem<ListItem<Object>>> setupIterator(
        List<Object> elements, int curserPos) {
        AbstractSelfOrganizingList<Object> list = spy(new AbstractSelfOrganizingList<>(elements.toArray()) {

            @Override
            public @NotNull Strategy strategy() {
                return null;
            }

            @Override
            public Object get(int index) throws IndexOutOfBoundsException {
                return toList(this).get(index);
            }
        });

        BidirectionalIterator<Object> iter = spy(list.iterator());

        int finalCurserPos = curserPos;

        Answer<?> defaultAnswer = (mockInvocation) -> {
            List<Object> mapped = toList(list);
            ListIterator<Object> listIter = mapped.listIterator();
            ReflectionUtilsP.setFieldValue(listIter, "cursor", finalCurserPos);
            ReflectionUtilsP.setFieldValue(listIter, "lastRet", finalCurserPos - 1);

            Object returned = ListIterator.class.getMethod(
                    mockInvocation.getMethod().getName(),
                    Arrays.stream(mockInvocation.getArguments()).map(ignored -> Object.class).toArray(Class[]::new)
                )
                .invoke(listIter, mockInvocation.getArguments());

            mergeInto(list, mapped);
            ReflectionUtilsP.setFieldValue(list, "size", mapped.size());

            ListItem<Object> cursor = list.head;
            if (mockInvocation.getMethod().getName().equals("next") || mockInvocation.getMethod().getName().equals("previous")) {
                ListItem<Object> lastReturned = list.head;
                while (lastReturned != null && lastReturned.key != returned) {
                    lastReturned = lastReturned.next;
                }

                ReflectionUtilsP.setFieldValue(iter, "lastReturned", lastReturned);

                int currentIndex = ReflectionUtilsP.getFieldValue(listIter, "cursor");

                while (currentIndex > 0 && cursor != null) {
                    cursor = cursor.next;
                    currentIndex--;
                }

                ReflectionUtilsP.setFieldValue(iter, "cursor", cursor);
            }
            if (mockInvocation.getMethod().getName().equals("add")) {
                ListItem<ListItem<Object>> previous = new ListItem<>(new ListItem<>(mockInvocation.getArguments()[0]));
                previous.next = ReflectionUtilsP.getFieldValue(iter, "previouses");
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);
            }
            if (mockInvocation.getMethod().getName().equals("next")) {
                ListItem<ListItem<Object>> previous = new ListItem<>(list.head);
                int currentIndex = ReflectionUtilsP.<Integer>getFieldValue(listIter, "cursor") - 1;

                while (currentIndex > 0) {
                    ListItem<ListItem<Object>> next = new ListItem<>(previous.key.next);
                    next.next = previous;
                    previous = next;
                    currentIndex--;
                }
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);
            }
            if (mockInvocation.getMethod().getName().equals("add") || mockInvocation.getMethod().getName().equals("remove")) {
                ReflectionUtilsP.setFieldValue(iter, "lastReturned", null);
            }
            if (mockInvocation.getMethod().getName().equals("previous") || mockInvocation.getMethod()
                .getName()
                .equals("remove")) {
                ReflectionUtilsP.setFieldValue(
                    iter,
                    "previouses",
                    ReflectionUtilsP.<ListItem<Object>>getFieldValue(iter, "previouses").next
                );
            }
            return returned;
        };

        doAnswer(defaultAnswer).when(iter).hasPrevious();
        doAnswer(defaultAnswer).when(iter).previous();
        doAnswer(defaultAnswer).when(iter).hasNext();
        doAnswer(defaultAnswer).when(iter).next();
        doAnswer(defaultAnswer).when(iter).add(any());
        doAnswer(defaultAnswer).when(iter).remove();

        if (curserPos != 0) {
            ListItem<Object> cursor = list.head;
            while (curserPos > 0 && cursor != null) {
                ReflectionUtilsP.setFieldValue(iter, "lastReturned", cursor);
                ReflectionUtilsP.setFieldValue(iter, "cursor", cursor.next);

                ListItem<ListItem<Object>> previous = new ListItem<>(cursor);
                previous.next = ReflectionUtilsP.getFieldValue(iter, "previouses");
                ReflectionUtilsP.setFieldValue(iter, "previouses", previous);

                cursor = cursor.next;
                curserPos--;
            }
        }

        doThrow(new AssertionFailedError("Illegal call to method addLast() from List")).when(list).addLast(any());
        doThrow(new AssertionFailedError("Illegal call to method addFirst() from List")).when(list).addFirst(any());
        doThrow(new AssertionFailedError("Illegal call to method remove() from List")).when(list).remove(any());
        doThrow(new AssertionFailedError("Illegal call to method get() from List")).when(list).get(anyInt());

        return new Triple<>(list, iter, ReflectionUtilsP.getFieldValue(iter, "previouses"));
    }

    public static <T> void mergeInto(AbstractSelfOrganizingList<T> toMergeInto, List<T> toMerge) {
        AbstractSelfOrganizingList<T> data = (AbstractSelfOrganizingList<T>) new MoveToFrontListIterative<>(toMerge.toArray());

        if (toMergeInto.head == null) {
            toMergeInto.head = data.head;
            toMergeInto.tail = data.tail;
            ReflectionUtilsP.setFieldValue(toMergeInto, "size", data.size());
            return;
        }

        if (toMerge.isEmpty()){
            toMergeInto.head = null;
            toMergeInto.tail = null;
            return;
        }

        ListItem<T> intoLast = null;
        ListItem<T> intoHead = toMergeInto.head;
        ListItem<T> dataHead = data.head;
        while (true) {
            //all following elements newly created
            if (intoHead == null){
                toMergeInto.tail.next = dataHead;
                toMergeInto.tail = data.tail;
                break;
            }
            toMergeInto.tail = intoHead;

            //all following elements deleted
            if (dataHead == null) {
                if (intoLast != null) {
                    intoLast.next = null;
                    toMergeInto.tail = null;
                }
                break;
            }

            //object stayed the same
            if (intoHead.key == dataHead.key) {
                intoLast = intoHead;
                intoHead = intoHead.next;
                dataHead = dataHead.next;
                continue;
            }

            //element added in between
            if (dataHead.next != null && intoHead.key == dataHead.next.key) {
                ListItem<T> inserted = new ListItem<>(dataHead.key);
                inserted.next = intoHead;
                if (intoLast == null) {
                    toMergeInto.head = inserted;
                } else {
                    intoLast.next = inserted;
                }
                dataHead = dataHead.next;
                continue;
            }

            //element deleted
            if (intoHead.next != null && intoHead.next.key == dataHead.key) {
                if (intoLast == null) {
                    toMergeInto.head = intoHead.next;
                    intoHead = intoHead.next;
                } else {
                    intoLast.next = intoHead.next;
                    intoHead = intoHead.next;
                }
                continue;
            }

            ListItem<T> current = toMergeInto.head;
            while (current.next != intoHead) {
                current = current.next;
            }
            current.next = current.next.next;
            intoLast = intoHead;
            intoHead = intoHead.next;
        }
    }
}
