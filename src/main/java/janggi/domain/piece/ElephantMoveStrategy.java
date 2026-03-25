package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to)) {
                    return;
                }
                Position next = to.add(dir.next().row(), dir.next().column());
                if (board.containsKey(next)) {
                    return;
                }

                next = next.add(dir.next().row(), dir.next().column());
                if (!board.containsKey(next) || !board.get(next).isSameDynasty(dynasty)) {
                    canMovePositions.add(next);
                }

                Position prev = to.add(dir.prev().row(), dir.prev().column());
                if (board.containsKey(prev)) {
                    return;
                }

                prev = prev.add(dir.prev().row(), dir.prev().column());
                if (!board.containsKey(prev) || !board.get(prev).isSameDynasty(dynasty)) {
                    canMovePositions.add(prev);
                }
            });
        }

        return canMovePositions;
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position from, Position to) {
        return false;
    }

}
