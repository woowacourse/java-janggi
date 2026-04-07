package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final MoveStrategy ELEPHANT_MOVE_STRATEGY = new ElephantMoveStrategy();

    public static MoveStrategy instance() {
        return ELEPHANT_MOVE_STRATEGY;
    }

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.nextPositionByDirection(dir).ifPresent(to -> {
                if (board.isEmpty(to)) {
                    canPassByDirection(to, dir.next(), board, dynasty, placeablePositions);
                    canPassByDirection(to, dir.prev(), board, dynasty, placeablePositions);
                }
            });
        }

        return placeablePositions;
    }

    private static void canPassByDirection(Position from, Direction dir,
                                           BoardSnapshot board, Dynasty dynasty,
                                           List<Position> placeablePositions) {
        from.nextPositionByDirection(dir).ifPresent(to -> {
            if (board.isEmpty(to)) {
                canMoveByDirection(to, dir, board, dynasty, placeablePositions);
            }
        });
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           BoardSnapshot board, Dynasty dynasty,
                                           List<Position> placeablePositions) {
        from.nextPositionByDirection(dir).ifPresent(to -> {
            if (board.isPlaceable(to, dynasty)) {
                placeablePositions.add(to);
            }
        });
    }

}
