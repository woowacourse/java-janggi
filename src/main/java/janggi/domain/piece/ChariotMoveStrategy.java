package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final ChariotMoveStrategy CHARIOT_MOVE_STRATEGY = new ChariotMoveStrategy();

    public static MoveStrategy instance() {
        return CHARIOT_MOVE_STRATEGY;
    }

    @Override
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> allPositions = from.findAllPositionsByDirection(dir);
            List<Position> positions = board.selectUntilNearestPiecePosition(allPositions);

            if (!positions.isEmpty()) {
                placeablePositions.addAll(removeIfCannotCatch(board, positions, dynasty));
            }
        }

        return placeablePositions;
    }

    private List<Position> removeIfCannotCatch(BoardSnapshot board, List<Position> positions, Dynasty dynasty) {
        if (board.isSameDynasty(positions.getLast(), dynasty)) {
            positions.removeLast();
        }
        return positions;
    }

}
