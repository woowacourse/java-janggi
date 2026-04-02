package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final HorseMoveStrategy horseMoveStrategy = new HorseMoveStrategy();

    private HorseMoveStrategy() {
    }

    public static HorseMoveStrategy getInstance() {
        return horseMoveStrategy;
    }

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirections()) {
            from.findOnePositionByDirection(dir).ifPresent(to -> {
                if (isPiecePresent(board, to)) {
                    return;
                }
                canMoveByDirection(to, dir.next(), board, dynasty, movablePositions);
                canMoveByDirection(to, dir.prev(), board, dynasty, movablePositions);
            });
        }

        return movablePositions;
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty, List<Position> canMovePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (!isPiecePresent(board, to) || isEnemy(board.get(to), dynasty)) {
                canMovePositions.add(to);
            }
        });
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    private static boolean isEnemy(Piece piece, Dynasty dynasty) {
        return !piece.isAlly(dynasty);
    }

}
