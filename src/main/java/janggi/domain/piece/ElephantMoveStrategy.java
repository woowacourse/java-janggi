package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final ElephantMoveStrategy ELEPHANT_MOVE_STRATEGY = new ElephantMoveStrategy();

    public static MoveStrategy instance() {
        return ELEPHANT_MOVE_STRATEGY;
    }

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to)) {
                    return;
                }
                canPassByDirection(to, dir.next(), board, dynasty, canMovePositions);
                canPassByDirection(to, dir.prev(), board, dynasty, canMovePositions);
            });
        }

        return canMovePositions;
    }

    private static void canPassByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findPositionByDirection(dir).ifPresent(to -> {
            if (board.containsKey(to)) {
                return;
            }
            canMoveByDirection(to, dir, board, dynasty, canMovePositions);
        });
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

}
