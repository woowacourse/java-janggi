package janggi.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vectors vectors1 = (Vectors) o;
        return Objects.equals(vectors, vectors1.vectors);
    }
}
