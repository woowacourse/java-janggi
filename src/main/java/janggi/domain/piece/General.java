package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class General implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.GENERAL;

    private final TeamType teamType;

    public General(final TeamType teamType) {
        this.teamType = teamType;
    }
}
