package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Chariot implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.CHARIOT;

    private final TeamType teamType;

    public Chariot(final TeamType teamType) {
        this.teamType = teamType;
    }

}
