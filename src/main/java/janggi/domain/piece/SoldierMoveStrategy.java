package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            if (dir.equals(dynasty.front().back())) {
                continue;
            }
            from.findOnePositionByDirection(dir).ifPresent(to -> {
                if (board.containsKey(to) && board.get(to).isSameDynasty(dynasty)) {
                    return;
                }
                movablePositions.add(to);
            });
        }
        return movablePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

}
