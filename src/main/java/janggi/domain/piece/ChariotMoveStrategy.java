package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> positions = from.findPositionsByDirection(dir);
            for (Position to : positions) {
                if (board.containsKey(to)) {
                    // 다른 팀을 만났을 때
                    if (!board.get(to).isSameDynasty(dynasty)) {
                        movablePositions.add(to);
                    }
                    break;
                }
                movablePositions.add(to);
            }
        }

        return movablePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

}
