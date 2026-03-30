package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    private static final CannonMoveStrategy cannonMoveStrategy = new CannonMoveStrategy();

    private CannonMoveStrategy() {
    }

    public static CannonMoveStrategy getInstance() {
        return cannonMoveStrategy;
    }

    // TODO: 궁성 관련 로직 추가
    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirections()) {
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
            if (isEnemy(dynasty, piece) && !isCannon(piece)) {
                movablePositions.add(to);
            }
            return movablePositions;
        }

        return movablePositions;
    }

    private static boolean isEnemy(Dynasty dynasty, Piece piece) {
        return !piece.isSameDynasty(dynasty);
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
