package piece;

import java.util.List;
import java.util.Set;
import position.Movement;
import position.Position;

public class General {
    private static final Set<List<Movement>> pieceMovements = Set.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT));

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        return pieceMovements.stream()
                .map(route -> fromPosition.findMoveablePositions(route))
                .filter(path -> path.contains(toPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

}
