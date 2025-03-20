package piece;

@FunctionalInterface
public interface QuadruplePredicate<A, B, C, D> {
    boolean test(A a, B b, C c, D d);
}
