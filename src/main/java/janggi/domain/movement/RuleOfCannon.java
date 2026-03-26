package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class RuleOfCannon implements Rule {

    private final List<Movement> movementOrder;

    public RuleOfCannon(final Direction direction) {
        this.movementOrder = generateMovementOrder(direction);
    }

    private List<Movement> generateMovementOrder(final Direction direction) {
        return List.of(
            new Movement(MAXIMUM_ROW, direction),
            new Movement(MAXIMUM_ROW, direction));
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Movement firstMovement = movementOrder.getFirst();
        final Movement secondMovement = movementOrder.getLast();
        final Piece piece = boardMediator.getPieceInPosition(from);

        // 포다리로 이동
        from = firstMovement.calculateBlockedPosition(from, piece, boardMediator);

        // 포다리로 판정된 위치에 실제로는 기물이 없거나 위치에 포가 존재하는 경우 이동 불가능
        if (!boardMediator.existsInPosition(from)
            || boardMediator.getPieceInPosition(from).getPieceType() == PieceType.CANNON) {
            return List.of();
        }

        final List<Position> traces = new ArrayList<>(
            secondMovement.calculateTraces(from, piece, boardMediator));
        return traces;
    }
}
