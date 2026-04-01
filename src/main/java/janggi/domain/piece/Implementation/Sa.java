package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractNormalPiece;
import janggi.domain.status.Team;

public class Sa extends AbstractNormalPiece {

    private static final int SCORE = 3;

    public Sa(Team team) {
        super(SCORE, team, PieceType.SA);
    }
}
