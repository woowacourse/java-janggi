package domain.board;

import domain.movement.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Intersection {

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

    private final Row row;
    private final File file;

    public Intersection(Row row, File file) {
        this.row = row;
        this.file = file;
    }

    public Intersection(int row, int file) {
        this(new Row(row), new File(file));
    }

    public boolean isOutOfBoard() {
        return row.isOutOfBoard() || file.isOutOfBoard();
    }

    public boolean isInBoard() {
        return !isOutOfBoard();
    }

    public boolean isPalace() {
        return NO_VECTOR_PALACES.contains(this)
                || DESCENDING_DIAGONAL_PALACES.contains(this)
                || ASCENDING_DIAGONAL_PALACES.contains(this);
    }

    public List<Vector> getPalaceDiagonalVectors() {
        ArrayList<Vector> vectors = new ArrayList<>();
        if (DESCENDING_DIAGONAL_PALACES.contains(this)) {
            vectors.add(Vector.leftUp());
            vectors.add(Vector.rightDown());
        }
        if (ASCENDING_DIAGONAL_PALACES.contains(this)) {
            vectors.add(Vector.leftDown());
            vectors.add(Vector.rightUp());
        }

        return Collections.unmodifiableList(vectors);
    }

    public int getRow() {
        return row.value();
    }

    public int getFile() {
        return file.value();
    }

    public boolean hasDifferentRow(int row) {
        return this.row.isDifferent(row);
    }

    public boolean hasDifferentFile(int file) {
        return this.file.isDifferent(file);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Intersection that = (Intersection) o;

        return Objects.equals(row, that.row) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, file);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "row=" + row +
                ", file=" + file +
                '}';
    }
}
