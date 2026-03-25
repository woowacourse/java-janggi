package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        return List.of();
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position from, Position to) {
        return false;
    }
    
}
