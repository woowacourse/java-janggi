package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final TeamType teamType = this.teamType;
        final TeamType otherTeamType = ((AbstractPiece) object).teamType;
        final PieceType pieceType = this.pieceType();
        final PieceType otherPieceType = ((AbstractPiece) object).pieceType();
        return teamType == otherTeamType && pieceType == otherPieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamType, pieceType());
    }
}
