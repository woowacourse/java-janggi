package piece;

import game.Board;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import position.Movement;
import position.Position;

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

    public List<Position> findPathForMove(Position fromPosition, Position toPosition) {
        if (!toPosition.onPalace()) {
            throw new IllegalArgumentException("왕은 궁성 내에서만 이동할 수 있습니다.");
        }
        Set<List<Movement>> combinedMovements = new HashSet<>(pieceMovements);

        if (fromPosition.isCenterOfPalace() || toPosition.isCenterOfPalace()) {
            combinedMovements.addAll(palaceMovements);
        }

        List<Position> path = combinedMovements.stream()
                .map(fromPosition::findMovablePositions)
                .filter(findPathByDestination(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
        return path.subList(0, path.size() - 1);
    }

    private static Predicate<List<Position>> findPathByDestination(final Position toPosition) {
        return path -> !path.isEmpty() && path.getLast().equals(toPosition);
    }

    @Override
    public void validatePath(final List<Position> positions, Board board) {
        if (positions.stream()
                .anyMatch(board::hasPieceAt)) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }

}
