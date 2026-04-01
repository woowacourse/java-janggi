package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.List;

public interface MovePolicy {

    List<Position> apply(Board board, Position start, List<Direction> directions);
}
