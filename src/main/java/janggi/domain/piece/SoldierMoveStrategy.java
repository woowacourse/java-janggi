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
    public List<Position> canMovePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            if (dir.equals(dynasty.front().back())) {
                continue;
            }
            from.nextPositionByDirection(dir).ifPresent(to -> {
                if (board.hasPieceAt(to) && board.isSameDynasty(to, dynasty)) {
                    return;
                }
                canMovePositions.add(to);
            });
        }
        return canMovePositions;
    }

}
