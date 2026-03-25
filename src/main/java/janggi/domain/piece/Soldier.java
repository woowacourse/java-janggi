package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Soldier implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;
    private final TeamType teamType;

    public Soldier(final TeamType teamType) {
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

}
