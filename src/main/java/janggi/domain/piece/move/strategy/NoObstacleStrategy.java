package janggi.domain.piece.move.strategy;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Path;
import janggi.domain.piece.Piece;
import janggi.domain.piece.move.MoveStrategy;

public class NoObstacleStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        Path path = piece.calculatePath(start, end);
        return janggiBoard.isNoObstacleOnPath(path.getMovedPoints(start, end));
    }
}
