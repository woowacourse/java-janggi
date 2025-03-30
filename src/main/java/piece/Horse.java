package piece;

import game.Board;
import position.Movement;
import position.Path;
import position.Position;

import java.util.List;
import java.util.Set;

public class Horse extends Piece {
    private static final Set<List<Movement>> pieceMovements = Set.of(
            List.of(Movement.UP, Movement.UP_UP_LEFT),
            List.of(Movement.UP, Movement.UP_UP_RIGHT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT),
            List.of(Movement.LEFT, Movement.UP_LEFT_LEFT),
            List.of(Movement.LEFT, Movement.DOWN_LEFT_LEFT),
            List.of(Movement.RIGHT, Movement.UP_RIGHT_RIGHT),
            List.of(Movement.RIGHT, Movement.DOWN_RIGHT_RIGHT));

    public Horse(final Country country) {
        super(PieceType.HORSE, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {
        return pieceMovements.stream()
                .map(fromPosition::findMovablePath)
                .filter(path -> !path.isEmpty() && path.isDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."))
                .withoutLast();
    }

    @Override
    public void validatePath(final Path path, Board board) {
        path.validateNoObstacles(board);
    }
}
