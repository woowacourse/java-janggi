package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Guard implements Piece{

    private static final PieceType PIECE_TYPE = PieceType.GUARD;

    private final TeamType teamType;

    public Guard(final TeamType teamType) {
        this.teamType = teamType;
    }
}
