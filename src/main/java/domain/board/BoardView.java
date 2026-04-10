package domain.board;

import domain.place.moveStrategy.Direction;
import domain.position.Position;
import java.util.Set;

public interface BoardView {
    boolean isEmpty(Position position);

    boolean isCannon(Position position);

    boolean isInPalace(Position position);

    boolean isPalaceConnected(Position position, Direction direction);

    Set<Position> findPalaceNextPositions(Position position);

    Set<Direction> findAvailableDirections(Position position);
}
