package janggi.domain.piece;

import janggi.domain.exception.DomainException;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Palace;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralMoveStrategy implements MoveStrategy {

    public static final String GENERAL_POSITION_STATE_ERROR = "궁 기물은 궁성안에 존재해야 합니다.";

    private static final GeneralMoveStrategy generalMoveStrategy = new GeneralMoveStrategy();

    private GeneralMoveStrategy() {
    }

    public static GeneralMoveStrategy getInstance() {
        return generalMoveStrategy;
    }

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {

        if (!Palace.isPalace(from)) {
            throw new DomainException(GENERAL_POSITION_STATE_ERROR);
        }
        List<Position> movablePositions = new ArrayList<>();
        for (Direction dir : Palace.getMovableDirectionsAtPalace(from)) {
            addIfMovable(board, from, dynasty, dir, movablePositions);
        }

        return movablePositions;
    }

    private static void addIfMovable(Map<Position, Piece> board, Position from, Dynasty dynasty, Direction dir, List<Position> movablePositions) {
        from.findOnePositionByDirection(dir).ifPresent(to -> {
            if (isPiecePresent(board, to) && board.get(to).isAlly(dynasty)) {
                return;
            }
            movablePositions.add(to);
        });
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

}
