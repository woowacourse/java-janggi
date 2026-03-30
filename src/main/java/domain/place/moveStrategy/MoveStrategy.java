package domain.place.moveStrategy;

import domain.place.Place;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    List<Position> getPath(Position from);

    boolean canMove(Map<Position, Place> path, Position from, Position to);

}
