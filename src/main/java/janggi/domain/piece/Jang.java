package janggi.domain.piece;

import janggi.domain.status.Team;

public class Jang extends AbstractNormalPiece {
    public Jang(Team team) {
        super(0, team, PieceType.JANG);
    }
}
