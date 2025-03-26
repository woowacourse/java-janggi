package model.piece;

import java.util.List;
import model.Movement;
import model.position.Position;

public interface MovableNavigator {

    List<Position> find(Position departure, Position arrival, List<Movement> movements);
}
