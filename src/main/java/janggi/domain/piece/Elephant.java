package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Elephant implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;

    private final TeamType teamType;

    public Elephant(final TeamType teamType) {
        this.teamType = teamType;
    }
}
