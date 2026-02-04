package h12;

import org.tudalgo.algoutils.tutor.general.SpoonUtils;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions4.*;

public final class TestUtils {

    private static Factory SPOON_FACTORY;
    private static final Map<Class<?>, CtTypeReference<?>> SPOON_TYPE_CACHE = new HashMap<>();

    public static void checkVA(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        CtType<?> ctType = getSpoonTypeRef(clazz).getTypeDeclaration();
        CtTypeReference<?>[] parameterTypeRefs = Arrays.stream(parameterTypes)
            .map(TestUtils::getSpoonTypeRef)
            .toArray(CtTypeReference[]::new);
        CtMethod<?> ctMethod = ctType.getMethod(methodName, parameterTypeRefs);
        Function<String, String> makeFailMsg = failMsg -> "%s.%s(%s) %s".formatted(clazz.getSimpleName(),
            methodName,
            Arrays.stream(parameterTypes).map(Class::getSimpleName).collect(Collectors.joining(", ")),
            failMsg);

        assertIsOneStatement(ctMethod.getBody(), emptyContext(), r ->
            makeFailMsg.apply("does not have exactly one statement in its body"));
        assertIsNotIteratively(ctMethod, emptyContext(), r ->
            makeFailMsg.apply("uses loops"));
        assertIsNotRecursively(ctMethod, emptyContext(), r ->
            makeFailMsg.apply("uses recursion"));
    }

    private static CtTypeReference<?> getSpoonTypeRef(Class<?> clazz) {
        return SPOON_TYPE_CACHE.computeIfAbsent(clazz, cls -> {
            if (cls.getName().startsWith("h12.")) {
                CtType<?> ctType = SpoonUtils.getType(cls.getName());
                if (SPOON_FACTORY == null) {
                    SPOON_FACTORY = ctType.getFactory();
                }
                return ctType.getReference();
            } else {
                return SPOON_FACTORY.createCtTypeReference(clazz);
            }
        });
    }
}
