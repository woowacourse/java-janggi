package team.janggi.domain.piece.strategy;

import team.janggi.domain.Position;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.piece.Piece;

public class SoldierMoveStrategy implements MoveStrategy {
    private final ForwardDirection forwardDirection;

    public SoldierMoveStrategy(ForwardDirection direction) {
        this.forwardDirection = direction;
    }

    @Override
    public boolean calculateMove(Position from, Position to, BoardStateReader stateReader) {
        return (isForward(from, to) || isSideMove(from, to)) && canKill(from, to, stateReader);
    }

    private boolean isForward(Position from, Position to) {
        if (forwardDirection == ForwardDirection.TOWARD_TOP) {
            return from.y() - to.y() == 1 && from.x() == to.x();
        }

        if (forwardDirection == ForwardDirection.TOWARD_BOTTOM) {
            return to.y() - from.y() == 1 && from.x() == to.x();
        }

        return false;
    }

    private boolean isSideMove(Position from, Position to) {
        return from.y() == to.y() && Math.abs(from.x() - to.x()) == 1;
    }

    private boolean canKill(Position from, Position to, BoardStateReader stateReader) {
        Piece currentPiece = stateReader.getPiece(from);
        Piece destinationPiece = stateReader.getPiece(to);
        return !currentPiece.isSameTeam(destinationPiece);
    }
}
