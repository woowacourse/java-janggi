package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractNormalPiece;
import janggi.domain.status.Team;

public class Sa extends AbstractNormalPiece {

    public Sa(Team team) {
        super(team, PieceType.SA);
    }
}
