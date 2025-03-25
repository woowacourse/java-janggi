package domain.position;

import java.util.List;

public enum PalacePositions {

    DIAGONAL_MOVABLE_PALACE_POSITION(
            List.of(
                new JanggiPosition(1, 4),
                new JanggiPosition(1, 6),
                new JanggiPosition(2, 5),
                new JanggiPosition(3, 4),
                new JanggiPosition(3, 6),
                new JanggiPosition(8, 4),
                new JanggiPosition(8, 6),
                new JanggiPosition(9, 5),
                new JanggiPosition(0, 4),
                new JanggiPosition(0, 6)
            )
    ),
    ORTHOGONAL_MOVABLE_PALACE_POSITION(
            List.of(
                new JanggiPosition(1, 5),
                new JanggiPosition(2, 4),
                new JanggiPosition(2, 6),
                new JanggiPosition(3, 5),
                new JanggiPosition(8, 5),
                new JanggiPosition(9, 4),
                new JanggiPosition(9, 6),
                new JanggiPosition(0, 5)
            )
    );

    private final List<JanggiPosition> positions;

    PalacePositions(List<JanggiPosition> positions) {
        this.positions = positions;
    }

    public static boolean isPalace(JanggiPosition janggiPosition) {
        return isDiagonalMovablePalacePosition(janggiPosition) || isOrthogonalMovablePalacePosition(janggiPosition);
    }

    public static boolean isDiagonalMovablePalacePosition(JanggiPosition janggiPosition) {
        return DIAGONAL_MOVABLE_PALACE_POSITION.positions.contains(janggiPosition);
    }

    private static boolean isOrthogonalMovablePalacePosition(JanggiPosition janggiPosition) {
        return ORTHOGONAL_MOVABLE_PALACE_POSITION.positions.contains(janggiPosition);
    }
}
