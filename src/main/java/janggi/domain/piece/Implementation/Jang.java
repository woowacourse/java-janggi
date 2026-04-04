package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractNormalPiece;
import janggi.domain.status.Team;

public class Jang extends AbstractNormalPiece {
    public Jang(Team team) {
        super(team, PieceType.JANG);
    }
}
