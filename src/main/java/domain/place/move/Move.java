package domain.place.move;

import domain.place.Place;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public interface Move {

    List<Position> getPath(Position from);

    boolean canMove(Map<Position, Place> path, Position from, Position to);

}
