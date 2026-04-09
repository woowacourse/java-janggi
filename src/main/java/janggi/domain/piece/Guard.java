package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.pieceaction.GuardAction;
import janggi.domain.pieceaction.PieceAction;
import janggi.domain.team.TeamType;
import java.util.List;

public class Guard implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.GUARD;
    private static final PieceAction PIECE_ACTION = new GuardAction();

    private final TeamType teamType;

    public Guard(final TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public boolean isOnSameTeamAs(final Piece other) {
        return this.teamType == other.getTeamType();
    }

    @Override
    public boolean isSameTypeAs(final Piece other) {
        return PIECE_TYPE == other.getPieceType();
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator) {
        return PIECE_ACTION.calculateMovablePositions(from, boardMediator);
    }

    @Override
    public boolean canCatch(final Piece target) {
        return !target.isOnSameTeamAs(this);
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
    }

    @Override
    public double getScore() {
        return PIECE_TYPE.getScore();
    }
}
