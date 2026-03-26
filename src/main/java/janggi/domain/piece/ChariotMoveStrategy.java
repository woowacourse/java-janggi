package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> positions = from.findPositionsByDirection(dir);
            for (Position to : positions) {
                if (board.containsKey(to)) {
                    // 다른 팀을 만났을 때
                    if (!board.get(to).isSameDynasty(dynasty)) {
                        canMovePositions.add(to);
                    }
                    break;
                }
                canMovePositions.add(to);
            }
        }

        return canMovePositions;
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position from, Position to) {
        return false;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

}
