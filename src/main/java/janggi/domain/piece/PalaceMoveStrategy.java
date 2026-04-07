package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class PalaceMoveStrategy implements MoveStrategy {

    private static final MoveStrategy PALACE_MOVE_STRATEGY = new PalaceMoveStrategy();

    public static MoveStrategy instance() {
        return PALACE_MOVE_STRATEGY;
    }

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : from.directions()) {
            from.nextPositionByDirection(dir).ifPresent(to -> {
                if (board.isPlaceable(to, dynasty) && to.isPalace(dynasty)) {
                    placeablePositions.add(to);
                }
            });
        }
        return placeablePositions;
    }

}
