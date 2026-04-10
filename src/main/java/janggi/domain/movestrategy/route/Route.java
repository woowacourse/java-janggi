package janggi.domain.movestrategy.route;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;
import janggi.exception.business.BusinessException;

import java.util.List;

public class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean isMatch(Position from, Position to) {
        try {
            Position current = from;
            for (Direction direction : directions) {
                current = direction.move(current);
            }
            return current.equals(to);
        } catch (BusinessException e) {
            return false;
        }
    }

    public boolean isPathClear(Position from, BoardState boardState) {
        Position current = from;
        for (int i = 0; i < directions.size() - 1; i++) {
            try {
                current = directions.get(i).move(current);
                if (boardState.hasPieceAt(current)) {
                    return false;
                }
            } catch (BusinessException e) {
                return false;
            }
        }
        return true;
    }
}
