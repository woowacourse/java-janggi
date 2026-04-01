package janggi.domain.piece;

import janggi.domain.status.Team;

public class Sa extends AbstractNormalPiece {

    private static final int SCORE = 3;

    public Sa(Team team) {
        super(SCORE, team, PieceType.SA);
    }
}
