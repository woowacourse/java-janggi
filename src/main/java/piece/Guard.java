package piece;

import game.Board;
import position.Movement;
import position.Path;
import position.Position;

import java.util.List;
import java.util.Set;

public class Guard extends Piece {
    private static final Set<List<Movement>> pieceMovements = Set.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT));
    private static final Set<List<Movement>> palaceMovements = Set.of(
            List.of(Movement.UP_LEFT),
            List.of(Movement.UP_RIGHT),
            List.of(Movement.DOWN_LEFT),
            List.of(Movement.DOWN_RIGHT)
    );
    public Guard(final Country country) {
        super(PieceType.GUARD, country);
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
