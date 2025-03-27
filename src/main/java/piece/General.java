package piece;

import java.util.List;
import java.util.Set;
import position.Movement;
import position.Position;

public class General extends Piece {
    private static final Set<List<Movement>> pieceMovements = Set.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT));

    public General(final Country country) {
        super(PieceType.GENERAL, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        return pieceMovements.stream()
                .map(route -> fromPosition.findMovablePositions(route))
                .filter(path -> !path.isEmpty() && path.getLast().equals(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

}
