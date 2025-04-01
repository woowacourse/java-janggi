package model.piece.movement;

import java.util.List;
import model.position.Position;

public interface RouteFinder {
    List<Position> calculateAllRoute(Position departure, Position arrival);
}
