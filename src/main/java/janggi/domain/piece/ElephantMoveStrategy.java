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
                to.findPositionByDirection(dir.next()).ifPresent(next -> {
                    if (board.containsKey(next)) {
                        return;
                    }
                    next.findPositionByDirection(dir.next()).ifPresent(next2 -> {
                        if (!board.containsKey(next2) || !board.get(next2).isSameDynasty(dynasty)) {
                            canMovePositions.add(next2);
                        }
                    });
                });

                to.findPositionByDirection(dir.prev()).ifPresent(prev -> {
                    if (board.containsKey(prev)) {
                        return;
                    }
                    prev.findPositionByDirection(dir.prev()).ifPresent(prev2 -> {
                        if (!board.containsKey(prev2) || !board.get(prev2).isSameDynasty(dynasty)) {
                            canMovePositions.add(prev2);
                        }
                    });
                });
            });
        }

        return canMovePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
    }

}
