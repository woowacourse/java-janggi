package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.piece.PieceType;
import janggi.domain.vo.Position;

import java.util.ArrayList;
import java.util.List;

public class CannonMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (!from.isStraightLine(to)) {
            return false;
        }

        List<Position> piecePositionsBetween = findPiecePositionsBetween(from, to, board);

        return hasOneBridgeNotCannon(piecePositionsBetween, board) && !isTargetCannon(to, board);
    }

    private boolean hasOneBridgeNotCannon(List<Position> positions, BoardView board) {
        return positions.size() == 1 && board.findByPosition(positions.get(0)).pieceType() != PieceType.CANNON;
    }

    private boolean isTargetCannon(Position to, BoardView board) {
        return board.findByPosition(to).pieceType() == PieceType.CANNON;
    }

    private List<Position> findPiecePositionsBetween(Position from, Position to, BoardView board) {
        List<Position> piecePositions = new ArrayList<>();
        Direction direction = Direction.between(from, to);
        Position pathPosition = from;

        while(pathPosition.hasNext(direction)) {
            pathPosition = pathPosition.nextPosition(direction);

            if (pathPosition.equals(to)) {
             break;
            }

            if (!board.isEmptyPosition(pathPosition)) {
                piecePositions.add(pathPosition);
            }
        }

        return piecePositions;
    }
}
