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
            if (isBackMove(dynasty, dir)) {
                continue;
            }
            tryMove(board, from, dynasty, dir, movablePositions);
        }
        return movablePositions;
    }


    private static boolean isBackMove(Dynasty dynasty, Direction dir) {
        return dir.equals(dynasty.front().back());
    }

    private static void tryMove(Map<Position, Piece> board, Position from, Dynasty dynasty, Direction dir, List<Position> movablePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (board.containsKey(to) && board.get(to).isSameDynasty(dynasty)) {
                return;
            }
            movablePositions.add(to);
        });
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

}
