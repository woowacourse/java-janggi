package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final ElephantMoveStrategy ELEPHANT_MOVE_STRATEGY = new ElephantMoveStrategy();

    public static MoveStrategy instance() {
        return ELEPHANT_MOVE_STRATEGY;
    }

    @Override
    public List<Position> canMovePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.hasPieceAt(to)) {
                    return;
                }
                canPassByDirection(to, dir.next(), board, dynasty, canMovePositions);
                canPassByDirection(to, dir.prev(), board, dynasty, canMovePositions);
            });
        }

        return canMovePositions;
    }

    private static void canPassByDirection(Position from, Direction dir,
                                           BoardSnapshot board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findPositionByDirection(dir).ifPresent(to -> {
            if (board.hasPieceAt(to)) {
                return;
            }
            canMoveByDirection(to, dir, board, dynasty, canMovePositions);
        });
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
