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
    public List<Position> findPlaceablePositions(BoardSnapshot board, Position from, Dynasty dynasty) {
        List<Position> placeablePositions = new ArrayList<>();
        for (Direction dir : Direction.valuesFourDirection()) {
            List<Position> allPositions = from.findAllPositionsByDirection(dir);
            List<Position> positions = board.selectUntilNearestPiecePosition(allPositions);

            if (!positions.isEmpty()) {
                findPlaceablePositionsAfterJump(board, dynasty, dir, positions.getLast(), placeablePositions);
            }
        }
        return placeablePositions;
    }

    private static void findPlaceablePositionsAfterJump(BoardSnapshot board, Dynasty dynasty, Direction dir,
                                                        Position from, List<Position> placeablePositions) {
        if (!board.isSamePieceType(from, CANNON)) {
            List<Position> allPositions = from.findAllPositionsByDirection(dir);
            List<Position> positions = board.selectUntilNearestPiecePosition(allPositions);
            
            if (!positions.isEmpty()) {
                placeablePositions.addAll(removeIfCannotCatch(board, dynasty, positions));
            }
        }
    }

    private static List<Position> removeIfCannotCatch(BoardSnapshot board, Dynasty dynasty, List<Position> positions) {
        if (board.isSamePieceType(positions.getLast(), CANNON) || board.isSameDynasty(positions.getLast(), dynasty)) {
            positions.removeLast();
        }
        return positions;
    }

}
