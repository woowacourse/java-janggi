package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveRule implements MoveRule {

    private final List<Movement> movementOrder;

    public CannonMoveRule(final Direction direction) {
        this.movementOrder = generateMovementOrder(direction);
    }

    private List<Movement> generateMovementOrder(final Direction direction) {
        return List.of(
                new Movement(MAXIMUM_ROW, direction),
                new Movement(MAXIMUM_ROW, direction));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Movement findBridgeMovement = movementOrder.getFirst();
        final Movement findTraceesMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);
        from = findBridgeMovement.findFirstOccupiedPositionOrMax(from, piece, boardMediator);
        if (!boardMediator.existsInPosition(from)
                || boardMediator.getPieceInPosition(from).getPieceType() == PieceType.CANNON) {
            return List.of();
        }
        final List<Position> traces = new ArrayList<>(findTraceesMovement.calculateTraces(from, piece, boardMediator));
        return traces;
    }
}
