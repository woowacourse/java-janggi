package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;

public class CannonMoveRule implements MoveRule {

    private final Movement movement;

    public CannonMoveRule(final Direction direction) {
        this.movement = new Movement(MAXIMUM_ROW, direction);
    }

    @Override
    public List<Position> execute(Position from, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        from = movement.findFirstOccupiedPositionOrMax(from, piece, boardMediator);  // 포다리 찾기
        if (isInvalidBridge(from, boardMediator)) {
            return List.of();
        }
        final List<Position> traces = new ArrayList<>(movement.calculateTraces(from, piece, boardMediator));
        return traces;
    }

    // 포다리가 안되는 경우 검증(빈 공간인지 or 포다리가 포 인지)
    private boolean isInvalidBridge(final Position bridge, final BoardMediator boardMediator) {
        return !boardMediator.existsInPosition(bridge)
                || boardMediator.getPieceInPosition(bridge).getPieceType() == PieceType.CANNON;
    }
}
