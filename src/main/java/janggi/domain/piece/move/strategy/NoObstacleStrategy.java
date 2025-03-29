package janggi.domain.piece.move.strategy;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.PathCalculator;

public class NoObstacleStrategy implements MoveStrategy {

    private final PathCalculator pathCalculator;

    public NoObstacleStrategy(PathCalculator pathCalculator) {
        this.pathCalculator = pathCalculator;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        Path path = pathCalculator.calculate(start, end);
        return janggiBoard.isNoObstacleOnPath(path.getMovedPoints(start, end));
    }
}
