package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();

        for (Direction dir : Direction.valuesFourDirection()) {
            if (dir.equals(dynasty.front().back())) {
                continue;
            }
            from.findPositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to) && board.get(to).isSameDynasty(dynasty)) {
                    return;
                }
                canMovePositions.add(to);
            });
        }
        return canMovePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

}
