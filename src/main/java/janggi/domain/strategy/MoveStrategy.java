package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.EnumSet;

public interface MoveStrategy {

    Destinations findDestinations(Position currentPosition, EnumSet<Direction> baseDirections, BoardInfo boardInfo);
}
