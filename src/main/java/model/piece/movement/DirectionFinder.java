package model.piece.movement;

import java.util.List;
import model.position.Position;

public interface DirectionFinder {
    List<Position> calculateAllDirection(Position departure, Position arrival);
}
