package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    int[][] offset = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    List<Position> canMovePositions(Map<Position, Piece> board, Position from);

    boolean canMove(Map<Position, Piece> board, Position from, Position to);

}
