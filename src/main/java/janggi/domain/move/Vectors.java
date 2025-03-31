package janggi.domain.move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Vectors(List<Vector> vectors) {

    public static Vectors of(Movement movement, Movement... remainMovements) {
        List<Movement> movements = new ArrayList<>();
        movements.add(movement);
        movements.addAll(Arrays.asList(remainMovements));

        List<Vector> vectorList = movements.stream()
                .map(Movement::getVector)
                .toList();

        return new Vectors(vectorList);
    }

    public static Vectors of(Vector vector, Vector... remainVectors) {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(vector);
        vectors.addAll(Arrays.asList(remainVectors));

        return new Vectors(vectors);
    }

    public static List<Vectors> rotate(List<Vectors> vectorsList) {
        return vectorsList.stream()
                .map(Vectors::vectors)
                .map(Vectors::rotateVectors)
                .map(Vectors::new)
                .toList();
    }

    private static List<Vector> rotateVectors(List<Vector> vectors) {
        return vectors.stream()
                .map(Vector::rotate)
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

    public int size() {
        return vectors().size();
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
