package h10;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.graderPrivateOnly;

public class H10_RubricProviderPublic implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H10.1 | StackOfObjects")
                .addChildCriteria(
                    criterion("Der generische Typparameter O wird korrekt deklariert und das Attribut objects wird korrekt initialisiert.",
                        1,
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H10_1_TestsPublic.class.getDeclaredMethod("testClassParameterPublic")),
                            JUnitTestRef.ofMethod(() -> H10_1_TestsPublic.class.getDeclaredMethod("testObjsTypePublic")))),
                    criterion("expandStack und shrinkStack werden korrekt mit generischem Typparameter angepasst.",
                        1,
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H10_1_TestsPublic.class.getDeclaredMethod("testExpandStackParameterPublic")),
                            JUnitTestRef.ofMethod(() -> H10_1_TestsPublic.class.getDeclaredMethod("testShrinkStackParameterPublic")))
                    ),
                    criterionPrivate("push und pop werden korrekt mit generischem Typparameter angepasst."),
                    criterionPrivate("get und set werden korrekt mit generischem Typparameter angepasst."),
                    criterionPrivate("of wird korrekt mit generischem Typparameter angepasst.")
                )
                .build(),
            Criterion.builder()
                .shortDescription("H10.2 | Generische Typen beschränken")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H10.2.1 | Interface Enclosure - Tier-Gehege Basis")
                        .addChildCriteria(
                            criterion("Der generische Typparameter von Enclosure wird korrekt deklariert und beschränkt.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_2_TestsPublic.class.getDeclaredMethod("testEnclosureClassParameterPublic"))),
                            criterion("getAnimals von Enclosure wird korrekt mit generischem Typparameter angepasst und beschränkt.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_2_TestsPublic.class.getDeclaredMethod("testGetAnimalsParameterPublic"))),
                            criterionPrivate("getMostCrowdedLocation gibt korrekt die Location mit der höchsten Anzahl an Tieren zurück.", 2)
                        ).build(),
                    Criterion.builder()
                        .shortDescription("H10.2.2 | Klasse AirEnclosure - Gehege für fliegende Tiere")
                        .addChildCriteria(
                            criterion("Der Klassenkopf von AirEnclosure ist korrekt mit generischem Typparameter angepasst und beschränkt.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_2_TestsPublic.class.getDeclaredMethod("testAirEnclosureClassParameterPublic"))),
                            criterionPrivate("getAnimals gibt korrekt animals zurück."),
                            criterion("feed füttert korrekt alle Tiere.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_2_TestsPublic.class.getDeclaredMethod("testFeedPublic")))
                        ).build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H10.3 | Bearbeitung von Enclosures mit funktionalen Interfaces")
                .addChildCriteria(
                    criterion("anyMatch hat korrekt beschränkte Typparameter.",
                        1,
                        JUnitTestRef.ofMethod(() -> H10_3_TestsPublic.class.getDeclaredMethod("testAnyMatchParameterPublic"))),
                    criterionPrivate("mapObjectOriented hat korrekt beschränkte Typparameter.", 2),
                    criterionPrivate("reduce hat korrekt beschränkte Typparameter.", 3),
                    criterion("mapFunctional hat korrekt beschränkte Typparameter.",
                        4,
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H10_3_TestsPublic.class.getDeclaredMethod("testMapFunctionalParameterPublic")),
                            JUnitTestRef.ofMethod(() -> H10_3_TestsPublic.class.getDeclaredMethod("testMapFunctionalReturnTypePublic"))
                        ))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H10.4 | Predicates and Consumer mit Lambda")
                .addChildCriteria(
                    criterion("HAS_2_LEGS funktioniert korrekt und wurde korrekt beschränkt.",
                        1,
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H10_4_TestsPublic.class.getDeclaredMethod("testHAS_2_LEGS_TypePublic")),
                            JUnitTestRef.ofMethod(() -> H10_4_TestsPublic.class.getDeclaredMethod("testHAS_2_LEGS_ImplementationPublic"))
                        )),
                    criterionPrivate("SIGN_WITH_ANIMAL_NAME funktioniert korrekt und wurde korrekt beschränkt.", 3),
                    criterion("Die Methode flyAndEat() gibt einen korrekten Consumer zurück, welcher korrekt beschränkt ist.",
                        2,
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> H10_4_TestsPublic.class.getDeclaredMethod("testFlyAndEatDefinedTypePublic")),
                            JUnitTestRef.ofMethod(() -> H10_4_TestsPublic.class.getDeclaredMethod("testFlyAndEatImplementationPublic")),
                            JUnitTestRef.ofMethod(() -> H10_4_TestsPublic.class.getDeclaredMethod("testFlyAndEatReturnTypePublic"))
                        ))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H10.5 | Enclosure::forEach")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H10.5.1 | Test Enclosure::anyMatch")
                        .addChildCriteria(
                            criterion("Zwei Lion und ein JugglingLion Objekte werden einem GroundEndclosure hinzugefügt.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_5_TestsPublic.class.getDeclaredMethod("testTestAnyMatchTwoLionsPublic"))),
                            criterionPrivate("Einfache Fehlfunktionen der Methode anyMatch werden durch assertFalse und assertTrue aufrufe erkannt.")
                        ).build(),

                    Criterion.builder()
                        .shortDescription("H10.5.2 | Test Enclosure::mapFunctional")
                        .addChildCriteria(
                            criterion("mapFunctional wird korrekt genutzt um Pinguin Objekte zwischen Enclosures zu bewegen und altern zu lassen.",
                                1,
                                JUnitTestRef.ofMethod(() -> H10_5_TestsPublic.class.getDeclaredMethod("testTestMapFunctionalPenguinPublic"))),
                            criterionPrivate("reduce wird korrekt genutzt um ein zusammengefassten String zu erstellen.")
                        ).build()
                )
                .build()
        )
        .build();

    private static Criterion criterionPrivate(String description) {
        return criterionPrivate(description, 1);
    }

    private static Criterion criterionPrivate(String description, int maxPoints) {
        return Criterion.builder()
            .shortDescription(description)
            .minPoints(0)
            .maxPoints(maxPoints)
            .grader(graderPrivateOnly(maxPoints))
            .build();
    }

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

}
