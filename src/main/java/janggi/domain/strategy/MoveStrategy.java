package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.EnumSet;

public interface MoveStrategy {

    Destinations findDestinations(Position currentPosition, EnumSet<Direction> baseDirections, BoardInfo boardInfo);
}
