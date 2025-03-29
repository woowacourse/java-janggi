package janggi.domain.piece.move;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import java.util.List;

public class AndMoveStrategy implements MoveStrategy {

    private final List<MoveStrategy> strategies;

    public AndMoveStrategy(List<MoveStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        for (MoveStrategy strategy : strategies) {
            if (!strategy.isMovable(janggiBoard, piece, start, end)) {
                return false;
            }
        }
        return true;
    }
}
