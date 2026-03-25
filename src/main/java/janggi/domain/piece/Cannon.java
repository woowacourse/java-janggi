package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public class Cannon implements Piece {

    private static final PieceType PIECE_TYPE = PieceType.CANNON;

    private final TeamType teamType;

    public Cannon(final TeamType teamType) {
        this.teamType = teamType;
    }
}
