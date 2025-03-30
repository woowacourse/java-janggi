package piece;

import game.Board;
import position.Movement;
import position.Path;
import position.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class General extends Piece {
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

    public General(final Country country) {
        super(PieceType.GENERAL, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {
        validateMoveInPalace(toPosition);
        Set<List<Movement>> combinedMovements = new HashSet<>(pieceMovements);
        if (fromPosition.isCenterOfPalace() || toPosition.isCenterOfPalace()) {
            combinedMovements.addAll(palaceMovements);
        }

        return combinedMovements.stream()
                .map(fromPosition::findMovablePath)
                .filter(path -> !path.isEmpty() && path.isDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."))
                .withoutLast();
    }

    private void validateMoveInPalace(final Position toPosition) {
        if (!toPosition.onPalace()) {
            throw new IllegalArgumentException("궁성 내에서만 이동할 수 있습니다.");
        }
    }

    @Override
    public void validatePath(final Path path, Board board) {
        path.validateNoObstacles(board);
    }
}
