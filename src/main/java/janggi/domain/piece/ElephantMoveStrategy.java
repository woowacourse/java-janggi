package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final ElephantMoveStrategy elephantMoveStrategy = new ElephantMoveStrategy();

    private ElephantMoveStrategy() {
    }

    public static ElephantMoveStrategy getInstance() {
        return elephantMoveStrategy;
    }

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirections()) {
            from.findOnePositionByDirection(dir).ifPresent(to -> {
                if (isPiecePresent(board, to)) {
                    return;
                }
                canPassByDirection(to, dir.next(), board, dynasty, movablePositions);
                canPassByDirection(to, dir.prev(), board, dynasty, movablePositions);
            });
        }

        return movablePositions;
    }

    private static void canPassByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (isPiecePresent(board, to)) {
                return;
            }
            canMoveByDirection(to, dir, board, dynasty, canMovePositions);
        });
    }

    private static void canMoveByDirection(Position from, Direction dir,
                                           Map<Position, Piece> board, Dynasty dynasty,
                                           List<Position> canMovePositions) {
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

    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
    }

}
