package domain.position;

import domain.direction.Direction;

import java.util.List;

public class JanggiPositionFactory {
    private static final List<JanggiPosition> castlePositions = List.of(
            new CastlePosition(0, 3, Direction.RIGHT_DOWN),
            new CastlePosition(1, 3),
            new CastlePosition(2, 3, Direction.RIGHT_UP),
            new CastlePosition(0, 4),
            new CastlePosition(1, 4, Direction.RIGHT_UP, Direction.RIGHT_DOWN, Direction.LEFT_UP, Direction.LEFT_DOWN),
            new CastlePosition(2, 4),
            new CastlePosition(0, 5, Direction.LEFT_DOWN),
            new CastlePosition(1, 5),
            new CastlePosition(2, 5, Direction.LEFT_UP),

            new CastlePosition(7, 3, Direction.RIGHT_DOWN),
            new CastlePosition(8, 3),
            new CastlePosition(9, 3, Direction.RIGHT_UP),
            new CastlePosition(7, 4),
            new CastlePosition(8, 4, Direction.RIGHT_UP, Direction.RIGHT_DOWN, Direction.LEFT_UP, Direction.LEFT_DOWN),
            new CastlePosition(9, 4),
            new CastlePosition(7, 5, Direction.LEFT_DOWN),
            new CastlePosition(8, 5),
            new CastlePosition(9, 5, Direction.LEFT_UP)
    );

    public static JanggiPosition of(final int row, final int col) {
        return castlePositions.stream()
                .filter(p -> p.getRow() == row && p.getCol() == col)
                .findFirst()
                .orElse(new NormalPosition(row, col));
    }
}
