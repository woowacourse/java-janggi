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
            boolean hasHopped = false;
            for (Position to : positions) {
                Piece piece = board.get(to);
                if (hasHopped) {
                    if (isPiecePresent(board, to)) {
                        if (!piece.isSameDynasty(dynasty) && !isCannon(piece)) {
                            canMovePositions.add(to);
                        }
                        break;
                    }
                    canMovePositions.add(to);
                }

                if (!hasHopped && isPiecePresent(board, to)) {
                    if (isCannon(piece)) {
                        break;
                    }
                    hasHopped = true;
                }
            }
        }

        return canMovePositions;
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    private boolean isCannon(Piece piece) {
        return PieceType.CANNON.equals(piece.pieceType());
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

}
