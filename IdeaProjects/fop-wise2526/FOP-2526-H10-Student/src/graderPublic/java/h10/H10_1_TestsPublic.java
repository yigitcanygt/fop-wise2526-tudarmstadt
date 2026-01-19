package h10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.declaration.CtMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

import static h10.H10_TestUtilsPublic.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class H10_1_TestsPublic {

    BasicTypeLink stackLink;
    Class<?> ctClassStack;
    Method expandStack;
    Method shrinkStack;
    Field objects;


    @BeforeEach
    public void setUpPublic() {
        stackLink = BasicTypeLink.of(StackOfObjects.class);
        ctClassStack = stackLink.reflection();
        expandStack = stackLink.getMethod(identical("expandStack")).reflection();
        shrinkStack = stackLink.getMethod(identical("shrinkStack")).reflection();
        objects = stackLink.getField(identical("objects")).reflection();
    }

    @Test
    public void testClassParameterPublic() {
        assertDefinedParameters(ctClassStack, Set.of(matchNoBounds("O")));
    }

    @Test
    public void testObjsTypePublic() {
        assertType(objects, matchArray(matchNoBounds("O")));
        assertNotNull(
            ReflectionUtilsPublic.getFieldValue(new StackOfObjects<>(), "objects"),
            emptyContext(),
            r -> "Field objects is not correctly initialized"
        );
    }

    @Test
    public void testExpandStackParameterPublic() {
        Type type = expandStack.getGenericReturnType();

        CtMethod<?> ctMethod = BasicMethodLink.of(expandStack).getCtElement();
        var actualNames = ctMethod.getType().toStringDebug().replace("\n", "");
        Context context = contextBuilder()
            .add("actual type", actualNames)
            .add("expected", "O[]")
            .build();

        assertEquals("O[]",
            type.toString(),
            context,
            r -> "expandStack is not adapted with correct generic type parameters.");
    }

    @Test
    public void testShrinkStackParameterPublic() {
        Type type = shrinkStack.getGenericReturnType();

        CtMethod<?> ctMethod = BasicMethodLink.of(shrinkStack).getCtElement();
        var actualNames = ctMethod.getType().toStringDebug().replace("\n", "");
        Context context = contextBuilder()
            .add("actual type", actualNames)
            .add("expected", "O[]")
            .build();

        assertEquals("O[]",
            type.toString(),
            context,
            r -> "expandStack is not adapted with correct generic type parameters.");
    }
}
