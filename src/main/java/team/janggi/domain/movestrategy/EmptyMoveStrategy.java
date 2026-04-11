package team.janggi.domain.movestrategy;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;

public class EmptyMoveStrategy implements MoveStrategy {
    private static final EmptyMoveStrategy INSTANCE = new EmptyMoveStrategy();

    private EmptyMoveStrategy() {
    }

    public static EmptyMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        return false;
    }
}
