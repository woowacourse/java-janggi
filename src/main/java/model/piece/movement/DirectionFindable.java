package model.piece.movement;

import java.util.List;
import model.position.Position;

public interface DirectionFindable {
    List<Position> calculateAllDirection(Position departure, Position arrival);
}
