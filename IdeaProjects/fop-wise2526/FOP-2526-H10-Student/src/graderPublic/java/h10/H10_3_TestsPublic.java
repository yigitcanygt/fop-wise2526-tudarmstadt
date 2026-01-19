package h10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static h10.H10_TestUtilsPublic.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class H10_3_TestsPublic {

    BasicTypeLink stackLink;
    Method anyMatch;
    Method mapFunctional;


    @BeforeEach
    public void setUpPublic() {
        stackLink = BasicTypeLink.of(Enclosure.class);
        anyMatch = stackLink.getMethod(identical("anyMatch")).reflection();
        mapFunctional = stackLink.getMethod(identical("mapFunctional")).reflection();
    }

    @Test
    public void testAnyMatchParameterPublic() {
        Predicate<Type> typeMatcher =
            getDefinedTypes(Enclosure.class, ".*").stream()
                .map(type -> matchWildcard(false, type))
                .reduce(Predicate::or)
                .orElse(new H10_TestUtilsPublic.GenericPredicate(i -> false, "Expected type is not defined"));

        assertParameters(anyMatch, List.of(matchNested(Predicate.class, typeMatcher)));
    }

    @Test
    public void testMapFunctionalParameterPublic() {
        // methode got B and E
        TypeVariable<?> typeB = mapFunctional.getTypeParameters()[0];
        TypeVariable<?> typeE = mapFunctional.getTypeParameters()[1];

        // A from Enclosure<A>
        TypeVariable<?> typeA = Enclosure.class.getTypeParameters()[0];

        Predicate<Type> aMatcher = match(typeA);
        Predicate<Type> bMatcher = match(typeB);
        Predicate<Type> eMatcher = match(typeE);

        // wildcards
        Predicate<Type> superA = matchWildcard(false, aMatcher);

        assertParameters(
            mapFunctional,
            List.of(
                // Supplier<E>
                matchNested(Supplier.class, eMatcher),

                // Function<? super A, B>
                matchNested(Function.class,
                    superA,   // first parameter: ? super A
                    bMatcher  // second parameter: B
                )
            )
        );
    }

    @Test
    public void testMapFunctionalReturnTypePublic() {
        Predicate<Type> methodTypeMatcher =
            Arrays.stream(mapFunctional.getTypeParameters())
                .map(type -> match(type))
                .reduce(Predicate::or)
                .orElse(new GenericPredicate(i -> false, "Expected method type not defined"));

        assertReturnParameter(mapFunctional, methodTypeMatcher);
    }

}
