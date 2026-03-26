package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class Cannon implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.CANNON;

    private final TeamType teamType;

    public Cannon(final TeamType teamType) {
        this.teamType = teamType;
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
    public boolean belongsToTeam(final TeamType teamType) {
        return this.teamType == teamType;
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from, final BoardMediator boardMediator) {
        return List.of();
    }
}
