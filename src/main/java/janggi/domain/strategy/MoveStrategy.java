package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.EnumSet;
import java.util.List;

public interface MoveStrategy {

    List<Position> destinationsOf(Position currentPosition, EnumSet<Direction> baseDirections, BoardInfo boardInfo);
}
