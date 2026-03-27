package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to)) {
                    return;
                }

                to.findPositionByDirection(dir.next()).ifPresent(next -> {
                    if (!board.containsKey(next) || !board.get(next).isSameDynasty(dynasty)) {
                        canMovePositions.add(next);
                    }
                });

                to.findPositionByDirection(dir.prev()).ifPresent(prev -> {
                    if (!board.containsKey(prev) || !board.get(prev).isSameDynasty(dynasty)) {
                        canMovePositions.add(prev);
                    }
                });
            });
        }

        return canMovePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

}
