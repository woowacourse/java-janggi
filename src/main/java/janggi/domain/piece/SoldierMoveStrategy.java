package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final SoldierMoveStrategy SOLDIER_MOVE_STRATEGY = new SoldierMoveStrategy();

    public static MoveStrategy instance() {
        return SOLDIER_MOVE_STRATEGY;
    }

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : from.directions()) {
            if (dir.equals(dynasty.front().back())) {
                continue;
            }
            from.nextPositionByDirection(dir).ifPresent(to -> {
                if (board.isEmpty(to) || !board.isSameDynasty(to, dynasty)) {
                    placeablePositions.add(to);
                }
            });
        }
        return placeablePositions;
    }

}
