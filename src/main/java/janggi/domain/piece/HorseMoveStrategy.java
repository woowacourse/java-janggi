package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final HorseMoveStrategy HORSE_MOVE_STRATEGY = new HorseMoveStrategy();

    public static MoveStrategy instance() {
        return HORSE_MOVE_STRATEGY;
    }
    
    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to)) {
                    return;
                }
                canMoveByDirection(to, dir.next(), board, dynasty, canMovePositions);
                canMoveByDirection(to, dir.prev(), board, dynasty, canMovePositions);
            });
        }

        return canMovePositions;
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findPositionByDirection(dir).ifPresent(to -> {
            if (!board.containsKey(to) || !board.get(to).isSameDynasty(dynasty)) {
                canMovePositions.add(to);
            }
        });
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

}
