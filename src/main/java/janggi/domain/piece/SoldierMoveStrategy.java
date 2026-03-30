package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final SoldierMoveStrategy soldierMoveStrategy = new SoldierMoveStrategy();

    private SoldierMoveStrategy() {
    }

    public static SoldierMoveStrategy getInstance() {
        return soldierMoveStrategy;
    }

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            addIfMovable(board, from, dynasty, dir, movablePositions);
        }
        return movablePositions;
    }

    private static void addIfMovable(Map<Position, Piece> board, Position from, Dynasty dynasty, Direction dir, List<Position> movablePositions) {
        if (isBackMove(dynasty, dir)) {
            return;
        }
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (isPiecePresent(board, to) && board.get(to).isSameDynasty(dynasty)) {
                return;
            }
            movablePositions.add(to);
        });
    }

    private static boolean isBackMove(Dynasty dynasty, Direction dir) {
        return dir.equals(dynasty.front().back());
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

}
