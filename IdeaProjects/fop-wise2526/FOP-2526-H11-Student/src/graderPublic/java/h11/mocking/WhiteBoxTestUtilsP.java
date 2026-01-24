package h11.mocking;

import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import spoon.reflect.code.CtBodyHolder;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WhiteBoxTestUtilsP {

    /**
     * @param classesToSearch a list of all classes to check for the given CtElement
     * @param toSearch        the CtElement to Search
     * @param bodyHolder      the element to search the CtElement in
     * @param <T>             the Type of element to find
     * @return a list containing all found elements
     */
    public static <T extends CtElement> Stream<T> getCtElements(final List<Class<?>> classesToSearch, final Class<T> toSearch,
                                                                final CtBodyHolder bodyHolder) {
        return getCtElements(classesToSearch, toSearch, bodyHolder, new ArrayList<>());
    }

    /**
     * @param classesToSearch a list of all classes to check for the given CtElement
     * @param toSearch        the CtElement to Search
     * @param bodyHolder      the element to search the CtElement in
     * @param <T>             the Type of element to find
     * @return a list containing all found elements
     */
    public static <T extends CtElement> Stream<T> getCtElements(final List<Class<?>> classesToSearch, final Class<T> toSearch,
                                                                final CtBodyHolder bodyHolder,
                                                                final List<CtBodyHolder> searched) {
        if (searched.contains(bodyHolder)) {
            return Stream.of();
        }
        searched.add(bodyHolder);

        return bodyHolder.getElements(new TypeFilter<>(CtElement.class) {
                @Override
                public boolean matches(final CtElement element) {
                    return (toSearch.isInstance(element) || (element instanceof CtInvocation<?>))
                        && classesToSearch.contains(element.getParent(CtMethod.class).getDeclaringType().getActualClass());
                }
            })
            .stream()
            .filter(ctElement -> !ctElement.equals(bodyHolder))
            .flatMap(element -> {
                if (element instanceof final CtInvocation<?> call) {
                    final var calledMethod = call.getExecutable();
                    if (calledMethod.getDeclaringType() == null
                        || !calledMethod.getDeclaringType()
                        .getQualifiedName()
                        .startsWith(ReflectionUtilsP.getExercisePrefix(classesToSearch.getFirst()))) {
                        return Stream.of();
                    }
                    if (calledMethod.getDeclaringType().isEnum()) {
                        return Stream.of();
                    }
                    final var actualCalledMethod = calledMethod.getActualMethod();

                    if (actualCalledMethod.getDeclaringClass().getEnclosingClass() != null) {
                        return Stream.of();
                    }
                    if (actualCalledMethod.getDeclaringClass().equals(ReflectionUtilsP.class)) {
                        return Stream.of();
                    }

                    final CtMethod<?> calledMethodCt = BasicMethodLink.of(actualCalledMethod).getCtElement();
                    return getCtElements(classesToSearch, toSearch, calledMethodCt, searched);
                }
                return Stream.of((T) element);
            });
    }
}
