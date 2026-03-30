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
            List<Position> positions = from.findAllPositionsByDirection(dir);
            movablePositions.addAll(filterMovablePositions(positions, board, dynasty));
        }

        return movablePositions;
    }

    private static List<Position> filterMovablePositions(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Position to : positions) {
            if (isPiecePresent(board, to)) {
                addIfEnemy(board, dynasty, to, movablePositions);
                break;
            }
            movablePositions.add(to);
        }

        return movablePositions;
    }

    private static void addIfEnemy(Map<Position, Piece> board, Dynasty dynasty, Position to, List<Position> movablePositions) {
        if (isEnemy(dynasty, board.get(to))) {
            movablePositions.add(to);
        }
    }

    private static boolean isEnemy(Dynasty dynasty, Piece piece) {
        return !piece.isSameDynasty(dynasty);
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

}
