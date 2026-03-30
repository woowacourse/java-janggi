package janggi.domain.piece;

import static janggi.domain.piece.PieceType.CANNON;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {

    private static final CannonMoveStrategy CANNON_MOVE_STRATEGY = new CannonMoveStrategy();

    public static MoveStrategy instance() {
        return CANNON_MOVE_STRATEGY;
    }

    @Override
    public List<Position> canMovePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> canMovePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> allPositions = from.findPositionsByDirection(dir);
            List<Position> positions = board.selectUntilNearestPiecePosition(allPositions);

            if (!positions.isEmpty() && !board.isSamePieceType(positions.getLast(), CANNON)) {
                List<Position> allPositions2 = positions.getLast().findPositionsByDirection(dir);
                List<Position> positions2 = board.selectUntilNearestPiecePosition(allPositions2);
                if (!positions2.isEmpty() && (board.isSamePieceType(positions2.getLast(), CANNON)
                        || board.isSameDynasty(positions2.getLast(), dynasty))) {
                    positions2.removeLast();
                }
                canMovePositions.addAll(positions2);
            }
        }

        return canMovePositions;
    }

}
