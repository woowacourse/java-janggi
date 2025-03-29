package janggi.domain.piece.move.strategy;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.PathCalculator;
import java.util.List;

public class JumpObstacleStrategy implements MoveStrategy {

    private final PathCalculator pathCalculator;

    public JumpObstacleStrategy(PathCalculator pathCalculator) {
        this.pathCalculator = pathCalculator;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        Path path = pathCalculator.calculate(start, end);
        List<Point> movedPoints = path.getMovedPoints(start, end);
        int piecesOnPath = janggiBoard.calculatePieceOnPath(movedPoints);
        return piecesOnPath == 1;
    }
}
