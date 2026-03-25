package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Horse implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.HORSE;

    private final TeamType teamType;

    public Horse(final TeamType teamType) {
        this.teamType = teamType;
    }
}
