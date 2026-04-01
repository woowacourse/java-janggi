package janggi.domain.piece;

import janggi.domain.status.Team;

public class Cha extends AbstractStraightPiece {

    private static final int SCORE = 13;

    public Cha(Team team) {
        super(SCORE, team, PieceType.CHA);
    }
}
