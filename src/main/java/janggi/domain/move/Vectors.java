package janggi.domain.move;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Vectors(List<Vector> vectors) {

    public static Vectors of(Movement... movements) {
        List<Vector> vectorList = Arrays.stream(movements)
                .map(Movement::getVector)
                .toList();

        return new Vectors(vectorList);
    }

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

    public Vector accumulate(int index) {
        if (index >= vectors.size() || index < 0) {
            throw new IllegalArgumentException("존재하지 않는 인덱스입니다.");
        }
        Vector accumulateVector = vectors.getFirst();

        for (int i = 1; i <= index; i++) {
            accumulateVector = accumulateVector.add(vectors.get(i));
        }

        return accumulateVector;
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
