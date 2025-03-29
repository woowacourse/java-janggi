package janggi.domain.piece.move;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import java.util.List;

public class OrMoveStrategy implements MoveStrategy {
    private final List<MoveStrategy> strategies;

    public OrMoveStrategy(List<MoveStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        for (MoveStrategy strategy : strategies) {
            try {
                if (strategy.isMovable(janggiBoard, piece, start, end)) {
                    return true;
                }
            } catch (Exception ex) {
                // 무시하고 다음 전략 진행
            }
        }
        return false;
    }
}
