package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public abstract class AbstractPiece implements Piece {
    protected final TeamType teamType;

    protected AbstractPiece(TeamType teamType) {
        this.teamType = teamType;
    }

    protected abstract PieceType getPieceType();

    protected abstract PieceAction getPieceAction();

    @Override
    public final boolean isSameTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    @Override
    public final List<Position> calculateMovablePositions(final Position from, final BoardMediator boardMediator) {
        return getPieceAction().calculateMovablePositions(from, this.teamType, boardMediator);
    }

    @Override
    public final TeamType teamType() {
        return this.teamType;
    }

    @Override
    public final PieceType pieceType() {
        return getPieceType();
    }
}
