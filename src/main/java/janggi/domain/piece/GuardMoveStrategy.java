package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class GuardMoveStrategy implements MoveStrategy {

    private static final GuardMoveStrategy GUARD_MOVE_STRATEGY = new GuardMoveStrategy();

    public static MoveStrategy instance() {
        return GUARD_MOVE_STRATEGY;
    }

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.nextPositionByDirection(dir).ifPresent(to -> {
                if (board.isEmpty(to) || !board.isSameDynasty(to, dynasty)) {
                    placeablePositions.add(to);
                }
            });
        }
        return placeablePositions;
    }

}
