package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findOnePositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to)) {
                    return;
                }
                canMoveByDirection(to, dir.next(), board, dynasty, movablePositions);
                canMoveByDirection(to, dir.prev(), board, dynasty, movablePositions);
            });
        }

        return movablePositions;
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
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
