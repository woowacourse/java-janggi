package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Palace;
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

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();

        List<Direction> defaultMovableDirections = List.of(Direction.valuesFourDirections());
        for (Direction dir : defaultMovableDirections) {
            List<Position> positions = from.findAllPositionsByDirection(dir);
            movablePositions.addAll(filterMovablePositionsInNotPalace(positions, board, dynasty));
        }

        if(Palace.isPalace(from)) {
            List<Direction> newDirectionsOfPalace = getNewDirectionsOfPalace(from, defaultMovableDirections);
            for (Direction dir : newDirectionsOfPalace) {
                List<Position> positions = from.findAllPositionsByDirection(dir);
                movablePositions.addAll(filterMovablePositionsInPalace(positions, board, dynasty));
            }
        }

        return movablePositions;
    }

    private static List<Direction> getNewDirectionsOfPalace(Position from, List<Direction> defaultMovableDirections) {
        List<Direction> newDirectionsOfPalace = new ArrayList<>(Palace.getMovableDirectionsAtPalace(from));
        newDirectionsOfPalace.removeAll(defaultMovableDirections);
        return newDirectionsOfPalace;
    }

    private List<Position> filterMovablePositionsInNotPalace(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        return filterMovablePositions(positions, board, dynasty, false);
    }

    private List<Position> filterMovablePositionsInPalace(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        return filterMovablePositions(positions, board, dynasty, true);
    }

    private List<Position> filterMovablePositions(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty, boolean isPalaceRelated) {
        List<Position> movablePositions = new ArrayList<>();
        boolean hasHopped = false;
        for (Position to : positions) {

            if(isPalaceRelated && !Palace.isPalace(to)) {
                break;
            }

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
        return !piece.isAlly(dynasty);
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    private static boolean isCannon(Piece piece) {
        return PieceType.CANNON.equals(piece.pieceType());
    }

}
