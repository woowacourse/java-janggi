package domain.board;

import domain.movement.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Palace {

    private static final Set<Intersection> NO_VECTOR_PALACES = Set.of(
            new Intersection(1, 5),
            new Intersection(2, 4),
            new Intersection(2, 6),
            new Intersection(3, 5),
            new Intersection(8, 5),
            new Intersection(9, 4),
            new Intersection(9, 6),
            new Intersection(10, 5)
    );
    private static final Set<Intersection> DESCENDING_DIAGONAL_PALACES = Set.of(
            new Intersection(1, 4),
            new Intersection(2, 5),
            new Intersection(3, 6),
            new Intersection(8, 4),
            new Intersection(9, 5),
            new Intersection(10, 6)
    );
    private static final Set<Intersection> ASCENDING_DIAGONAL_PALACES = Set.of(
            new Intersection(1, 6),
            new Intersection(2, 5),
            new Intersection(3, 4),
            new Intersection(8, 6),
            new Intersection(9, 5),
            new Intersection(10, 4)
    );

    public boolean contains(Intersection intersection) {
        return NO_VECTOR_PALACES.contains(intersection)
                || DESCENDING_DIAGONAL_PALACES.contains(intersection)
                || ASCENDING_DIAGONAL_PALACES.contains(intersection);
    }

    public List<Vector> getDiagonalVectors(Intersection intersection) {
        ArrayList<Vector> vectors = new ArrayList<>();
        if (DESCENDING_DIAGONAL_PALACES.contains(intersection)) {
            vectors.add(Vector.leftUp());
            vectors.add(Vector.rightDown());
        }
        if (ASCENDING_DIAGONAL_PALACES.contains(intersection)) {
            vectors.add(Vector.leftDown());
            vectors.add(Vector.rightUp());
        }

        return Collections.unmodifiableList(vectors);
    }
}
