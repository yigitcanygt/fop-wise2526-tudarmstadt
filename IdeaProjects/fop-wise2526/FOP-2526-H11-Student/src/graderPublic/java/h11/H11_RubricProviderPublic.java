package h11;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import java.util.List;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.graderPrivateOnly;

public class H11_RubricProviderPublic implements RubricProvider {

    private static final Criterion H11_1 = Criterion.builder()
        .shortDescription("H11.1 | Strategie Move to Front")
        .maxPoints(4)
        .minPoints(0)
        .addChildCriteria(
            criterionPrivate(
                "Die Methode get(int index) in MoveToFrontListRecursive verschiebt das Element am angegebenen Index an den "
                    + "Anfang der Liste."
            ),
            criterionPrivate(
                "Die Methode get(int index) in MoveToFrontListRecursive ist vollständig implementiert und funktioniert korrekt."
            ),
            criterion(
                "Die Methode get(int index) in MoveToFrontListIterative verschiebt das Element am angegebenen Index an den "
                    + "Anfang der Liste.",
                JUnitTestRef.ofMethod(() -> MoveToFrontListIterativeTestPublic.class.getDeclaredMethod(
                    "testGet",
                    List.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode get(int index) in MoveToFrontListIterative ist vollständig implementiert und funktioniert korrekt.",
                JUnitTestRef.ofMethod(() -> MoveToFrontListIterativeTestPublic.class.getDeclaredMethod(
                    "testGet_complete",
                    List.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            )
        )
        .build();

    private static final Criterion H11_2 = Criterion.builder()
        .shortDescription("H11.2 | Strategie Transpose")
        .maxPoints(6)
        .minPoints(0)
        .addChildCriteria(
            criterionPrivate(
                "Die Methode get(int index) in TransposeListRecursive liefert das Element am angegebenen Index korrekt zurück."
            ),
            criterionPrivate(
                "Die Methode get(int index) in TransposeListRecursive verschiebt das Element am angegebenen Index korrekt um "
                    + "eine Position nach vorne (mit seinem Vorgänger wird getauscht)."
            ),
            criterionPrivate(
                "Die Methode get(int index) in TransposeListRecursive ist vollständig implementiert und funktioniert korrekt."
            ),
            criterionPrivate(
                "Die Methode get(int index) in TransposeListIterative verschiebt das Element am angegebenen Index korrekt "
                    + "zurück."
            ),
            criterionPrivate(
                "Die Methode get(int index) in TransposeListIterative verschiebt das Element am angegebenen Index korrekt um "
                    + "eine Position nach vorne (mit seinem Vorgänger wird getauscht)."
            ),
            criterionPrivate(
                "Die Methode get(int index) in TransposeListIterative ist vollständig implementiert und funktioniert korrekt."
            )
        )
        .build();

    private static final Criterion H11_3 = Criterion.builder()
        .shortDescription("H11.3: Strategie Random")
        .maxPoints(6)
        .minPoints(0)
        .addChildCriteria(
            criterion(
                "Die Methode get(int index) in RandomRecursive vertauscht das Element am angegebenen Index korrekt mit dem Kopf"
                    + " der Liste.",
                JUnitTestRef.ofMethod(() -> RandomListRecursiveTestPublic.class.getDeclaredMethod(
                    "testGet_first",
                    List.class,
                    int.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode get(int index) in RandomRecursive vertauscht zwei benachbarte Elemente korrekt.",
                JUnitTestRef.ofMethod(() -> RandomListRecursiveTestPublic.class.getDeclaredMethod(
                    "testGet_neighbor",
                    List.class,
                    int.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode get(int index) in RandomRecursive vertauscht zwei nicht benachbarte Elemente korrekt.",
                JUnitTestRef.ofMethod(() -> RandomListRecursiveTestPublic.class.getDeclaredMethod(
                    "testGet_non_neighbor",
                    List.class,
                    int.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            ),
            criterionPrivate(
                "Die Methode get(int index) in RandomIterative vertauscht das Element am angegebenen Index korrekt mit dem Kopf"
                    + " der Liste."
            ),
            criterionPrivate(
                "Die Methode get(int index) in RandomIterative vertauscht zwei benachbarte Elemente korrekt."
            ),
            criterionPrivate(
                "Die Methode get(int index) in RandomIterative vertauscht zwei nicht benachbarte Elemente korrekt."
            )
        )
        .build();

    private static final Criterion H11_4_1 = Criterion.builder()
        .shortDescription("H11.4.1 | Bidirektionaler Iterator")
        .maxPoints(8)
        .minPoints(0)
        .addChildCriteria(
            criterion(
                "Die Methode hasNext() und hasPrevious() sind vollständig implementiert und funktionieren korrekt.",
                JUnitTestRef.and(
                    JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                        "testHasNext",
                        List.class,
                        int.class,
                        ListItem.class,
                        ListItem.class,
                        boolean.class
                    )),
                    JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                        "testHasPrevious",
                        List.class,
                        int.class,
                        ListItem.class,
                        ListItem.class,
                        boolean.class
                    ))
                )
            ),
            criterion(
                "Die Methode previous() ist vollständig implementiert und funktioniert korrekt.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testPrevious",
                    List.class,
                    int.class,
                    ListItem.class,
                    ListItem.class,
                    ListItem.class,
                    Object.class,
                    boolean.class
                ))
            ),
            criterion(
                "Die Methode next() ist vollständig implementiert und funktioniert korrekt.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testNext",
                    List.class,
                    int.class,
                    ListItem.class,
                    ListItem.class,
                    ListItem.class,
                    Object.class
                ))
            ),
            criterion(
                "Die Methode add() gibt das korrekte Ergebnis für Fall 1 zurück.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testAdd_empty",
                    List.class,
                    int.class,
                    Object.class,
                    ListItem.class,
                    ListItem.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode add() gibt das korrekte Ergebnis für Fall 2 zurück.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testAdd",
                    List.class,
                    int.class,
                    Object.class,
                    ListItem.class,
                    ListItem.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode remove() wirft eine IllegalStateException, wenn vor dem Aufruf kein Element durch den Iterator "
                    + "zurückgegeben wurde. (Fall 1)",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testRemove_exception",
                    List.class,
                    int.class,
                    ListItem.class,
                    ListItem.class,
                    List.class,
                    boolean.class
                ))
            ),
            criterion(
                "Die Methode remove() gibt das korrekte Ergebnis für Fall 2 zurück.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testRemove_previous",
                    List.class,
                    int.class,
                    ListItem.class,
                    ListItem.class,
                    List.class,
                    boolean.class
                ))
            ),
            criterion(
                "Die Methode remove() gibt das korrekte Ergebnis für Fall 3 zurück.",
                JUnitTestRef.ofMethod(() -> BidirectionalListIteratorTest.class.getDeclaredMethod(
                    "testRemove_next",
                    List.class,
                    int.class,
                    ListItem.class,
                    ListItem.class,
                    List.class,
                    boolean.class
                ))
            )
        )
        .build();

    private static final Criterion H11_4_2 = Criterion.builder()
        .shortDescription("H11.4.2 | Strategie Move to Front")
        .maxPoints(2)
        .minPoints(0)
        .addChildCriteria(
            criterionPrivate(
                "Die Methode get(int index) in MoveToFrontListIterator entfernt das zu verschiebende Element korrekt aus der "
                    + "Liste."
            ),
            criterionPrivate(
                "Die Methode get(int index) in MoveToFrontListIterator fügt das zu verschiebende Element korrekt am Anfang der "
                    + "Liste wieder ein."
            )
        )
        .build();

    private static final Criterion H11_4_3 = Criterion.builder()
        .shortDescription("H11.4.3 | Strategie Transpose")
        .maxPoints(3)
        .minPoints(0)
        .addChildCriteria(
            criterion(
                "Die Methode get(int index) in TransposeListIterator gibt das korrekte Element für den übergebenen Index zurück.",
                JUnitTestRef.ofMethod(() -> TransposeListIteratorTestPublic.class.getDeclaredMethod(
                    "testGet",
                    List.class,
                    int.class,
                    Object.class,
                    List.class
                ))
            ),
            criterion(
                "Die Methode get(int index) in TransposeListIterator fügt das erste Element (index) an der korrekten Position "
                    + "wieder ein.",
                JUnitTestRef.ofMethod(() -> TransposeListIteratorTestPublic.class.getDeclaredMethod(
                    "testGet_index", List.class, int.class, Object.class, List.class))
            ),
            criterion(
                "Die Methode get(int index) in TransposeListIterator fügt das zweite Element (vorheriger index) an der "
                    + "korrekten Position wieder ein.",
                JUnitTestRef.ofMethod(() -> TransposeListIteratorTestPublic.class.getDeclaredMethod(
                    "testGet_previousIndex", List.class, int.class, Object.class, List.class))
            )
        )
        .build();

    private static final Criterion H11_4_4 = Criterion.builder()
        .shortDescription("H11.4.4 | Strategie Random")
        .maxPoints(3)
        .minPoints(0)
        .addChildCriteria(
            criterionPrivate(
                "Die Methode get(int index) in RandomIterator fügt das erste Element (index) an der korrekten Position wieder "
                    + "ein."
            ),
            criterionPrivate(
                "Die Methode get(int index) in RandomIterator fügt das erste Element (index) an der korrekten Position wieder "
                    + "ein."
            ),
            criterionPrivate(
                "Die Methode get(int index) in RandomIterator fügt das zweite Element (zufälliger Index) an der korrekten "
                    + "Position wieder ein."
            )
        )
        .build();

    private static final Criterion H11_4 = Criterion.builder()
        .shortDescription("P11.4 | Iteratoren")
        .minPoints(0)
        .addChildCriteria(
            H11_4_1,
            H11_4_2,
            H11_4_3,
            H11_4_4
        )
        .build();

    private static Criterion criterionPrivate(String description) {
        return Criterion.builder()
            .shortDescription(description)
            .grader(graderPrivateOnly())
            .build();
    }

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H11 | Selbstorganisierende Listen")
        .addChildCriteria(
            H11_1,
            H11_2,
            H11_3,
            H11_4
        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
