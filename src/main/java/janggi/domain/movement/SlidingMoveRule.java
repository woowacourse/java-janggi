package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.List;

public class SlidingMoveRule implements MoveRule {

    private final Movement movementOrder;

    public SlidingMoveRule(Movement movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        final Piece piece = boardMediator.getPieceInPosition(from);
        return movementOrder.calculateTraces(from, piece, teamType, boardMediator);
    }
}
