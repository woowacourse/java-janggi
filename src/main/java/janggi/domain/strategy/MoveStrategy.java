package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    Paths findMovablePaths(Position current, EnumSet<Direction> directions);

    List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPieceVO);
}
