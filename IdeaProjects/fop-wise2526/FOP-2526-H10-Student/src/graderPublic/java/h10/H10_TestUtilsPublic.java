package h10;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

public class H10_TestUtilsPublic {

    /**
     * Creates a new Predicate that matches a Type if it has the given name and no bounds.
     *
     * @param name the name of the Type that should be matched. Also accepts a regex for the name.
     * @return a Predicate that matches a Type with the given name.
     */
    public static Predicate<Type> matchNoBounds(String name) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!type.getTypeName().matches(name)) {
                    return false;
                }
                if (bounds.getLeft() != null) {
                    return false;
                }
                return bounds.getRight().size() == 1 && bounds.getRight().get(0) == Object.class;
            },
            String.format("Name: %s; Bounds: No Bounds", name)
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it has the given name and bounds.
     *
     * @param name           the name of the Type that should be matched. Also accepts a regex for the name.
     * @param expectedBounds the lower Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a Type with the given name and bounds.
     */
    public static Predicate<Type> matchLowerBounds(String name, Type... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!type.getTypeName().matches(name)) {
                    return false;
                }
                return bounds.getLeft().size() == expectedBounds.length
                    && new HashSet<>(bounds.getLeft()).containsAll(List.of(expectedBounds));
            },
            String.format(
                "Name: %s; Bounds: %s",
                name,
                Arrays.stream(expectedBounds).map(Type::getTypeName).collect(Collectors.joining(", "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it has the given name and bounds.
     *
     * @param name           the name of the Type that should be matched. Also accepts a regex for the name.
     * @param expectedBounds the upper Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a Type with the given name and bounds.
     */
    public static Predicate<Type> matchLowerBounds(String name, Predicate<Type>... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!type.getTypeName().matches(name)) {
                    return false;
                }
                return bounds.getLeft().size() == expectedBounds.length
                    && bounds.getLeft()
                    .stream()
                    .allMatch(actual -> Stream.of(expectedBounds).anyMatch(expected -> expected.test(actual)))
                    && Stream.of(expectedBounds).allMatch(expected -> bounds.getLeft().stream().anyMatch(expected));
            },
            String.format(
                "Name: %s; Bounds: %s",
                name,
                Arrays.stream(expectedBounds).map(Object::toString).collect(Collectors.joining(", "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it has the given name and bounds.
     *
     * @param name           the name of the Type that should be matched. Also accepts a regex for the name.
     * @param expectedBounds the upper Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a Type with the given name and bounds.
     */
    public static Predicate<Type> matchUpperBounds(String name, Type... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!type.getTypeName().matches(name)) {
                    return false;
                }
                return bounds.getRight().size() == expectedBounds.length
                    && new HashSet<>(bounds.getRight()).containsAll(List.of(expectedBounds));
            },
            String.format(
                "Name: %s; Bounds: %s",
                name,
                Arrays.stream(expectedBounds).map(Type::getTypeName).collect(Collectors.joining(", "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it has the given name and bounds.
     *
     * @param name           the name of the Type that should be matched. Also accepts a regex for the name.
     * @param expectedBounds the upper Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a Type with the given name and bounds.
     */
    @SafeVarargs
    public static Predicate<Type> matchUpperBounds(String name, Predicate<Type>... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!type.getTypeName().matches(name)) {
                    return false;
                }
                return bounds.getRight().size() == expectedBounds.length
                    && bounds.getRight()
                    .stream()
                    .allMatch(actual -> Stream.of(expectedBounds).anyMatch(expected -> expected.test(actual)))
                    && Stream.of(expectedBounds).allMatch(expected -> bounds.getRight().stream().anyMatch(expected));
            },
            String.format(
                "Name: %s; Bounds: %s",
                name,
                Arrays.stream(expectedBounds).map(Object::toString).collect(Collectors.joining(", "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it is a wildcard type and has the given bounds.
     *
     * @param isUpperBound   indicates if the given Bounds should be the upper or lower bounds of the type
     * @param expectedBounds the expected Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a wildcard type with the given bounds.
     */
    public static Predicate<Type> matchWildcard(boolean isUpperBound, Type... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!(type instanceof WildcardType wildcardType)) {
                    return false;
                }
                if (isUpperBound) {
                    return bounds.getRight().size() == expectedBounds.length &&
                        new HashSet<>(bounds.getRight()).containsAll(List.of(expectedBounds));
                }
                return bounds.getLeft().size() == expectedBounds.length &&
                    new HashSet<>(bounds.getLeft()).containsAll(List.of(expectedBounds));
            },
            String.format(
                "Wildcard: ? %s %s",
                isUpperBound ? "extends" : "super",
                Arrays.stream(expectedBounds).map(Type::getTypeName).collect(Collectors.joining(" & "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it is a wildcard type and has the given bounds.
     *
     * @param isUpperBound   indicates if the given Bounds should be the upper or lower bounds of the type
     * @param expectedBounds the expected Bounds that a type should have to be matched by this Predicate.
     * @return a Predicate that matches a wildcard type with the given bounds.
     */
    @SafeVarargs
    public static Predicate<Type> matchWildcard(boolean isUpperBound, Predicate<Type>... expectedBounds) {
        return new GenericPredicate(
            (type) -> {
                Pair<List<Type>, List<Type>> bounds = getBounds(type);
                if (!(type instanceof WildcardType wildcardType)) {
                    return false;
                }
                if (isUpperBound) {
                    return bounds.getRight().size() == expectedBounds.length
                        && bounds.getRight()
                        .stream()
                        .allMatch(actual -> Stream.of(expectedBounds).anyMatch(expected -> expected.test(actual)))
                        && Stream.of(expectedBounds).allMatch(expected -> bounds.getRight().stream().anyMatch(expected));
                }
                return bounds.getLeft().size() == expectedBounds.length
                    && bounds.getLeft()
                    .stream()
                    .allMatch(actual -> Stream.of(expectedBounds).anyMatch(expected -> expected.test(actual)))
                    && Stream.of(expectedBounds).allMatch(expected -> bounds.getLeft().stream().anyMatch(expected));
            },
            String.format(
                "Wildcard: ? %s %s",
                isUpperBound ? "extends" : "super",
                Arrays.stream(expectedBounds).map(Object::toString).collect(Collectors.joining(" & "))
            )
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it is an exact match with the given Type.
     *
     * @param expected the type that is expected.
     * @return the Predicate matching the expected type.
     */
    public static Predicate<Type> match(Type expected) {
        return new GenericPredicate(
            (type) -> type.equals(expected),
            String.format("Type: %s", expected.getTypeName())
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it is a match with an array of the given Type.
     *
     * @param expected the type that is expected.
     * @return the Predicate matching the expected type.
     */
    public static Predicate<Type> matchArray(Type expected) {
        return new GenericPredicate(
            (type) -> {
                if (type instanceof GenericArrayType arrayType) {
                    return arrayType.getGenericComponentType().equals(expected);
                }
                return false;
            },
            String.format("Type: %s", expected.getTypeName())
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it is a match with an array of the given Type.
     *
     * @param expected the type that is expected.
     * @return the Predicate matching the expected type.
     */
    public static Predicate<Type> matchArray(Predicate<Type> expected) {
        return new GenericPredicate(
            (type) -> {
                if (type instanceof GenericArrayType arrayType) {
                    return expected.test(arrayType.getGenericComponentType());
                }
                return false;
            },
            String.format("Type: %s", expected.toString())
        );
    }

    /**
     * Creates a new Predicate that matches a Type if it has the expected outer Type and the inner Types all match the
     * expectedNested types
     *
     * @param outerType      the Type of the outer type.
     * @param expectedNested an array of predicates for all inner types that should be matched.
     * @return a predicate that checks the outer and inner types of a ParameterizedType.
     */
    @SafeVarargs
    public static Predicate<Type> matchNested(Type outerType, Predicate<Type>... expectedNested) {
        return new Predicate<>() {
            @Override
            public boolean test(Type type) {
                if (!(type instanceof ParameterizedType parameterizedType)) {
                    return false;
                }
                if (!parameterizedType.getRawType().equals(outerType)) {
                    return false;
                }
                Type[] actualType = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < expectedNested.length; i++) {
                    if (!expectedNested[i].test(actualType[i])) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return String.format("Type: %s; Inner Type: %s", outerType.getTypeName(), List.of(expectedNested));
            }
        };
    }

    /**
     * Matches if any of the supplied Types is passed to the predicate
     *
     * @param typesToMatch the types to match
     * @return a Predicate that checks if one of the supplied Types is tested
     */
    public static Predicate<Type> matchAny(Type... typesToMatch) {
        return Arrays.stream(typesToMatch)
            .map(H10_TestUtilsPublic::match)
            .reduce(Predicate::or)
            .orElse(new H10_TestUtilsPublic.GenericPredicate(i -> false, "Expected type is not defined"));
    }

    /**
     * Matches if any of the supplied Types is passed to the predicate
     *
     * @param typesToMatch the types to match
     * @return a Predicate that checks if one of the supplied Types is tested
     */
    public static Predicate<Type> matchAny(List<Type> typesToMatch) {
        return typesToMatch.stream()
            .map(H10_TestUtilsPublic::match)
            .reduce(Predicate::or)
            .orElse(new H10_TestUtilsPublic.GenericPredicate(i -> false, "Expected type is not defined"));
    }

    /**
     * This method returns the upper and lower bounds of the given type.
     *
     * <p>The returned Pair contains a list of lower bounds in the left Parameter and a list of upper bounds in the right
     * Parameter.
     *
     * <p>If the given Type does not have any lower bounds the left element of the Pair will be null.
     *
     * <p>If the given Type is not generic this method will return null.
     *
     * @param type the type to getrieve the Bounds from
     * @return a Pair containing both the upper and the lower bounds of the given Type
     */
    public static Pair<List<Type>, List<Type>> getBounds(Type type) {
        if (type instanceof WildcardType wildcardType) {
            return ImmutablePair.of(Arrays.asList(wildcardType.getLowerBounds()), Arrays.asList(wildcardType.getUpperBounds()));
        }
        if (type instanceof ParameterizedType parameterizedType) {
            return ImmutablePair.of(null, Arrays.asList(parameterizedType.getActualTypeArguments()));
        }
        if (type instanceof TypeVariable<?> typeVariable) {
            return ImmutablePair.of(null, Arrays.asList(typeVariable.getBounds()));
        }
        if (type instanceof GenericArrayType) {
            return ImmutablePair.of(null, List.of(Object[].class));
        }
        return null;
    }

    /**
     * Retrieves the inner type of the given Type.
     *
     * @param type the type to get the inner type from.
     * @return the inner type of the given type. Returns empty list if no inner type is present.
     */
    public static List<Type> getInnerTypes(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return List.of();
        }
        return Arrays.asList(parameterizedType.getActualTypeArguments());
    }

    /**
     * Retrieves the super Types of the given Type if it is a class. Returns upper bounds otherwise.
     *
     * @param type the type to get the super types from.
     * @return the super types or upper bounds of the given type. Returns a list containing only Object if no supertype can be
     * determined.
     */
    public static List<Type> getGenericSuperTypes(Type type) {
        if (type instanceof Class<?> clazz) {
            List<Type> superTypes = new ArrayList<>();
            if (clazz.getGenericSuperclass() != null) {
                superTypes.add(clazz.getGenericSuperclass());
            }
            if (clazz.getGenericInterfaces().length > 0) {
                superTypes.addAll(List.of(clazz.getGenericInterfaces()));
            }
            return superTypes;
        }

        Pair<List<Type>, List<Type>> bounds = getBounds(type);
        if (bounds != null) {
            return bounds.getRight();
        }
        return List.of(Object.class);
    }

    /**
     * Retrieves the return type of the given Method.
     *
     * @param method the method to get the return type from.
     * @return the return type of the given method.
     */
    public static Type getReturnType(Method method) {
        return method.getGenericReturnType();
    }

    /**
     * Retrieves all parameters of the given method. Returned parameters may not generic.
     *
     * @param method the method that the generic types should be retrieved from.
     * @param regex  a regex that is used to filter all generic type names.
     * @return a List containing all types of the parameters from the method whose type names match the given regex.
     */
    public static List<Type> getTypeParameters(Method method, String regex) {
        return Arrays.stream(method.getGenericParameterTypes()).filter(t -> t.getTypeName().matches(regex)).toList();
    }

    /**
     * Retrieves all generic types that are defined by the given method.
     *
     * @param method the method that the generic types should be retrieved from.
     * @param regex  a regex that is used to filter all generic type names.
     * @return a List containing all defined types that match the given regex.
     */
    public static List<Type> getDefinedTypes(Method method, String regex) {
        return Arrays.stream(method.getTypeParameters()).filter(t -> t.getTypeName().matches(regex)).map(t -> (Type) t).toList();
    }

    /**
     * Retrieves all generic types that are defined by the given class.
     *
     * @param clazz the class that the generic types should be retrieved from.
     * @param regex a regex that is used to filter all generic type names.
     * @return a List containing all defined types that match the given regex.
     */
    public static List<Type> getDefinedTypes(Class clazz, String regex) {
        return Arrays.stream(clazz.getTypeParameters()).filter(t -> t.getTypeName().matches(regex)).map(t -> (Type) t).toList();
    }

    /**
     * Asserts that the given {@link Class} defines a certain set a generic Parameters.
     *
     * @param clazz    the Class that should be tested.
     * @param expected a set of predicates that is used to check if all defined generic Types match an expected Type.
     */
    public static void assertDefinedParameters(Class<?> clazz, Set<Predicate<Type>> expected) {

        List<TypeVariable<?>> typeVariable = Arrays.asList(clazz.getTypeParameters());
        CtType<?> ctClass = (CtType<?>) BasicTypeLink.of(clazz).getCtElement();
        var actualNames =
            ctClass.getFormalCtTypeParameters().stream().map(CtType::toStringDebug).map(s -> s.replace("\n", "")).toList();
        Context context = contextBuilder()
            .add("expected", expected)
            .add("actual", actualNames)
            .build();

        assertTrue(
            !typeVariable.isEmpty(),
            emptyContext(),
            r -> clazz.getSimpleName() + " does not have any generic parameters."
        );

        assertEquals(
            expected.size(),
            typeVariable.size(),
            context,
            r -> clazz.getSimpleName() + " does not have the expected number of generic parameters."
        );
        typeVariable.forEach(a ->
            assertTrue(
                expected.stream().anyMatch(e -> e.test(a)),
                context,
                r -> String.format("The type parameter %s of %s do not match any expected types.", a, clazz.getSimpleName())
            )
        );
    }

    /**
     * Asserts that the given {@link Method} defines a specific set of generic types.
     *
     * @param method   the method that is checked for type definitions.
     * @param expected a set of predicates that is used to check if all defined generic Types match an expected Type.
     */
    public static void assertDefinedParameters(Method method, Set<Predicate<Type>> expected) {

        List<TypeVariable<?>> typeVariable = Arrays.asList(method.getTypeParameters());
        CtMethod<?> ctMethod = BasicMethodLink.of(method).getCtElement();
        var actualNames = ctMethod.getFormalCtTypeParameters()
            .stream()
            .map(CtTypeParameter::toStringDebug)
            .map(s -> s.replace("\n", ""))
            .toList();
        Context context = contextBuilder()
            .add("expected", expected)
            .add("actual", actualNames)
            .build();

        assertTrue(!typeVariable.isEmpty(), emptyContext(), r -> method.getName() + " does not have any generic parameters.");

        assertEquals(
            expected.size(),
            typeVariable.size(),
            context,
            r -> method.getName() + " does not have the expected number of generic parameters."
        );
        typeVariable.forEach(a ->
            assertTrue(
                expected.stream().anyMatch(e -> e.test(a)),
                context,
                r -> String.format("The type parameter %s of %s do not match any expected types.", a, method.getName())
            )
        );
    }

    /**
     * Asserts that the given {@link Method} has a return type that matches the given {@link Predicate}.
     *
     * @param method   the method that should be tested.
     * @param expected the {@link Predicate} that shoul be used to check the return type.
     */
    public static void assertReturnParameter(Method method, Predicate<Type> expected) {
        Type type = method.getGenericReturnType();

        CtMethod<?> ctMethod = BasicMethodLink.of(method).getCtElement();
        var actualNames = ctMethod.getType().toStringDebug().replace("\n", "");
        Context context = contextBuilder()
            .add("actual type", actualNames)
            .add("expected", expected)
            .build();

        assertTrue(expected.test(type), context, r -> String.format("%s has a wrong return type.", method.getName()));
    }

    /**
     * Asserts that the given {@link Method} has a correct list of parameters each parameter is checked with the given
     * {@link Predicate} for the index.
     *
     * @param method   the method that should be checked
     * @param expected a list containing a {@link Predicate} for each Parameter of the method.
     */
    public static void assertParameters(Method method, List<Predicate<Type>> expected) {
        Type[] type = method.getGenericParameterTypes();

        assertEquals(
            expected.size(),
            type.length,
            emptyContext(), r -> String.format("The method %s() does not have the correct amount of parameters", method.getName())
        );

        CtMethod<?> ctMethod = BasicMethodLink.of(method).getCtElement();
        var actualNames =
            ctMethod.getParameters()
                .stream()
                .map(CtParameter::getType)
                .map(CtTypeReference::toStringDebug)
                .map(s -> s.replace("\n", ""))
                .toList();

        for (int i = 0; i < type.length; i++) {
            int finalI = i;

            Context context = contextBuilder()
                .add("actual type", actualNames.get(i))
                .add("expected", expected.get(i))
                .build();

            assertTrue(
                expected.get(i).test(type[i]),
                context,
                r -> String.format("%s has a wrong parameter at index %d.", method.getName(), finalI)
            );
        }

    }

    /**
     * Asserts that the given field has a {@link Type} that matches the given {@link Predicate}.
     *
     * @param field    the field that should be checked.
     * @param expected the {@link Predicate} that is used to check if the Field has a correct Type.
     */
    public static void assertType(Field field, Predicate<Type> expected) {
        Type type = field.getGenericType();

        CtField<?> ctField =
            BasicTypeLink.of(field.getDeclaringClass()).getCtElement().filterChildren(new TypeFilter<>(CtField.class) {
                @Override
                public boolean matches(CtField element) {
                    return super.matches(element) && element.getSimpleName().equals(field.getName());
                }
            }).first();
        var actualNames = ctField.getType().toStringDebug();

        Context context = contextBuilder()
            .add("actual type", actualNames)
            .add("expected", expected)
            .build();

        assertTrue(expected.test(type), context, r -> String.format("%s has a wrong type.", field.getName()));

    }

    /**
     * Asserts that the given method is generic.
     *
     * @param toTest a method reference to the method that should be checked.
     */
    public static void assertGeneric(Method toTest) {

        Predicate<Method> isGeneric = (method) -> !getTypeParameters(method, ".*").isEmpty();
        isGeneric = isGeneric.or((method) -> getBounds(getReturnType(method)) != null);

        assertTrue(
            isGeneric.test(toTest),
            emptyContext(),
            r -> String.format("The method %s() is not Generic.", toTest.getName())
        );
    }

    /**
     * A simple Predicate that can store a custom toString() method for better readability.
     */
    public static class GenericPredicate implements Predicate<Type> {

        /**
         * The description that should be displayed if toString() is called.
         */
        private final String description;
        /**
         * The underlying predicate.
         */
        private final Predicate<Type> predicate;

        /**
         * Creates a new {@link GenericPredicate} from a {@link Predicate} and a short description that describes what the
         * predicate matches.
         *
         * @param predicate   the predicate that should be used to match any object.
         * @param description the description of what the predicate matches.
         */
        GenericPredicate(Predicate<Type> predicate, String description) {
            this.predicate = predicate;
            this.description = description;
        }

        @Override
        public boolean test(Type type) {
            return predicate.test(type);
        }

        @NotNull
        @Override
        public Predicate<Type> and(@NotNull Predicate<? super Type> other) {
            return new GenericPredicate(predicate.and(other), "(" + this.description + " and " + other + ")");
        }

        @NotNull
        @Override
        public Predicate<Type> negate() {
            return new GenericPredicate(predicate.negate(), "(not " + description + ")");
        }

        @NotNull
        @Override
        public Predicate<Type> or(@NotNull Predicate<? super Type> other) {
            return new GenericPredicate(predicate.or(other), "(" + this.description + " or " + other + ")");
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
