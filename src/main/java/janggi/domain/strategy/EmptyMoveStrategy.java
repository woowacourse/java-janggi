package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Paths;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EmptyMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> directions) {
        return new Paths();
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        return Collections.emptyList();
    }
}
