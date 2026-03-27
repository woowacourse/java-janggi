package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.board.Position;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    Paths findMovablePaths(Position current, EnumSet<Direction> directions);

    List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece);
}
