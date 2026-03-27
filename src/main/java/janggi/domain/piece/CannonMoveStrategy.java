package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> positions = from.findPositionsByDirection(dir);
            boolean isFirst = false;
            for (Position to : positions) {
                // 어떤 기물을 넘었을 때
                if (isFirst) {
                    if (board.containsKey(to)) {
                        if (!board.get(to).isSameDynasty(dynasty) && !isCannon(board.get(to))) {
                            canMovePositions.add(to);
                        }
                        break;
                    }
                    canMovePositions.add(to);
                }
                // 다른 기물을 만났을때
                if (!isFirst && board.containsKey(to)) {
                    if (isCannon(board.get(to))) {
                        break;
                    }
                    isFirst = true;
                }
            }
        }

        return canMovePositions;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    private boolean isCannon(Piece piece) {
        return PieceType.CANNON.equals(piece.pieceType());
    }

}
