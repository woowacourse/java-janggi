package janggi.domain.movement;

import java.util.Arrays;
import java.util.List;

public record Vectors(List<Vector> vectors) {

    public static Vectors of(Vector... vectors) {
        return new Vectors(Arrays.asList(vectors));
    }

    public static List<Vectors> rotate(List<Vectors> vectorsList) {
        return vectorsList.stream()
                .map(Vectors::vectors)
                .map(vectorRoute -> vectorRoute.stream()
                        .map(Vector::rotate)
                        .toList()
                )
                .map(Vectors::new)
                .toList();
    }
}
