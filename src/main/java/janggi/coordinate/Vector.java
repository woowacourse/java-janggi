package janggi.coordinate;

import java.util.ArrayList;
import java.util.List;

public record Vector(int deltaRow, int deltaColumn) {

    public static Vector of(final Position from, final Position to) {
        final Vector deltaRow = from.row().vectorTo(to.row());
        final Vector deltaColumn = from.column().vectorTo(to.column());
        return deltaRow.add(deltaColumn);
    }

    public static Vector create() {
        return new Vector(0, 0);
    }

    public Vector up() {
        return new Vector(deltaRow - 1, deltaColumn);
    }

    public Vector down() {
        return new Vector(deltaRow + 1, deltaColumn);
    }

    public Vector left() {
        return new Vector(deltaRow, deltaColumn - 1);
    }

    public Vector right() {
        return new Vector(deltaRow, deltaColumn + 1);
    }

    public Vector add(final Vector other) {
        return new Vector(this.deltaRow + other.deltaRow,
                this.deltaColumn + other.deltaColumn);
    }

    public Vector subtract(final Vector other) {
        return new Vector(this.deltaRow - other.deltaRow,
                this.deltaColumn - other.deltaColumn);
    }

    public Vector extractStraightForDiagonal() {
        if (isDiagonal()) {
            return new Vector(0, 0);
        }

        final int absRow = Math.abs(deltaRow);
        final int absColumn = Math.abs(deltaColumn);
        final int overDistance = Math.abs(absRow - absColumn);

        if (absRow > absColumn) {
            return new Vector(Integer.signum(deltaRow) * overDistance, 0);
        }
        return new Vector(0, Integer.signum(deltaColumn) * overDistance);
    }

    public List<Vector> splitToUnitVectors() {
        final int rowStep = Integer.signum(deltaRow);
        final int columnStep = Integer.signum(deltaColumn);

        final int absRow = Math.abs(deltaRow);
        final int absColumn = Math.abs(deltaColumn);

        final int diagonalSteps = Math.min(absRow, absColumn);
        final int rowSteps = absRow - diagonalSteps;
        final int columnSteps = absColumn - diagonalSteps;

        final List<Vector> unitVectors = new ArrayList<>();

        for (int i = 0; i < rowSteps; i++) {
            unitVectors.add(new Vector(rowStep, 0));
        }

        for (int i = 0; i < columnSteps; i++) {
            unitVectors.add(new Vector(0, columnStep));
        }

        for (int i = 0; i < diagonalSteps; i++) {
            unitVectors.add(new Vector(rowStep, columnStep));
        }

        return unitVectors;
    }

    public boolean isUpward() {
        return deltaRow < 0;
    }

    public boolean isDownward() {
        return deltaRow > 0;
    }

    private boolean isDiagonal() {
        return Math.abs(deltaRow) == Math.abs(deltaColumn);
    }
}
