package janggi.domain.piece.move.strategy;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.PathCalculator;

public class AvoidPieceOnPathStrategy implements MoveStrategy {

    private final PieceType avoidPieceType;
    private final PathCalculator pathCalculator;

    public AvoidPieceOnPathStrategy(PieceType avoidPieceType, PathCalculator pathCalculator) {
        this.avoidPieceType = avoidPieceType;
        this.pathCalculator = pathCalculator;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        Path path = pathCalculator.calculate(start, end);
        if (janggiBoard.hasPieceTypeOnPath(path.getMovedPoints(start, end), avoidPieceType)) {
            return false;
        }
        return true;
    }
}
