package janggi.domain.piece.move.strategy;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.move.MoveStrategy;

public class AvoidPieceOnPathStrategy implements MoveStrategy {

    private final PieceType avoidPieceType;

    public AvoidPieceOnPathStrategy(PieceType avoidPieceType) {
        this.avoidPieceType = avoidPieceType;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        Path path = piece.calculatePath(start, end);
        if (janggiBoard.hasPieceTypeOnPath(path.getMovedPoints(start, end), avoidPieceType)) {
            return false;
        }
        return true;
    }
}
