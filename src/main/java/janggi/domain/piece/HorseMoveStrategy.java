package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {

    private static final HorseMoveStrategy HORSE_MOVE_STRATEGY = new HorseMoveStrategy();

    public static MoveStrategy instance() {
        return HORSE_MOVE_STRATEGY;
    }

    @Override
    public List<Position> canMovePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.hasPieceAt(to)) {
                    return;
                }
                canMoveByDirection(to, dir.next(), board, dynasty, canMovePositions);
                canMoveByDirection(to, dir.prev(), board, dynasty, canMovePositions);
            });
        }

        return canMovePositions;
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           BoardSnapshot board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findPositionByDirection(dir).ifPresent(to -> {
            if (!board.hasPieceAt(to) || !board.isSameDynasty(to, dynasty)) {
                canMovePositions.add(to);
            }
        });
    }

}
