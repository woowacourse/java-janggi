package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> positions = from.findAllPositionsByDirection(dir);
            movablePositions.addAll(filterMovablePositions(positions, board, dynasty));
        }

        return movablePositions;
    }

    private List<Position> filterMovablePositions(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        boolean hasHopped = false;
        for (Position to : positions) {

            if (!hasHopped) {
                if (!isPiecePresent(board, to)) {
                    continue;
                }
                if (isCannon(board.get(to))) {
                    return movablePositions;
                }
                hasHopped = true;
                continue;
            }

            if (!isPiecePresent(board, to)) {
                movablePositions.add(to);
                continue;
            }

            Piece piece = board.get(to);
            if (!piece.isSameDynasty(dynasty) && !isCannon(piece)) {
                movablePositions.add(to);
            }
            return movablePositions;
        }

        return movablePositions;
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    private static boolean isCannon(Piece piece) {
        return PieceType.CANNON.equals(piece.pieceType());
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

}
