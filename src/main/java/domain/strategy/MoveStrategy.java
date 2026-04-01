package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.List;

public interface MoveStrategy {

    List<List<Direction>> calculatePotentialPaths(Position start);
}
