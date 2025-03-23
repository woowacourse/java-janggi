package janggi.coordinate;

import java.util.ArrayList;
import java.util.List;

public record Vector(int deltaRow, int deltaColumn) {

    public static Vector of(Position from, Position to) {
        Vector deltaRow = from.row().vectorTo(to.row());
        Vector deltaColumn = from.column().vectorTo(to.column());
        return deltaRow.add(deltaColumn);
    }

    public Vector add(Vector other) {
        return new Vector(this.deltaRow + other.deltaRow,
                this.deltaColumn + other.deltaColumn);
    }

    public Vector subtract(Vector other) {
        return new Vector(this.deltaRow - other.deltaRow,
                this.deltaColumn - other.deltaColumn);
    }

    public Vector extractStraightForDiagonal() {
        if (isDiagonal()) {
            return new Vector(0, 0);
        }

        int absRow = Math.abs(deltaRow);
        int absColumn = Math.abs(deltaColumn);
        int overDistance = Math.abs(absRow - absColumn);

        if (absRow > absColumn) {
            return new Vector(Integer.signum(deltaRow) * overDistance, 0);
        }
        return new Vector(0, Integer.signum(deltaColumn) * overDistance);
    }

    public List<Vector> splitToUnitVectors() {
        int rowStep = Integer.signum(deltaRow);
        int columnStep = Integer.signum(deltaColumn);

        int absRow = Math.abs(deltaRow);
        int absColumn = Math.abs(deltaColumn);

        int diagonalSteps = Math.min(absRow, absColumn);
        int rowSteps = absRow - diagonalSteps;
        int columnSteps = absColumn - diagonalSteps;

        List<Vector> unitVectors = new ArrayList<>();

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

    private boolean isDiagonal() {
        return Math.abs(deltaRow) == Math.abs(deltaColumn);
    }
}
