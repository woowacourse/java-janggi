package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final GeneralMoveStrategy generalMoveStrategy = new GeneralMoveStrategy();

    private GeneralMoveStrategy() {
    }

    public static GeneralMoveStrategy getInstance() {
        return generalMoveStrategy;
    }

    // TODO: 궁성 관련 로직 추가
    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesAllDirections()) {
            addIfMovable(board, from, dynasty, dir, movablePositions);
        }
        return movablePositions;
    }

    private static void addIfMovable(Map<Position, Piece> board, Position from, Dynasty dynasty, Direction dir, List<Position> movablePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (isPiecePresent(board, to) && board.get(to).isSameDynasty(dynasty)) {
                return;
            }
            movablePositions.add(to);
        });
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }

}
