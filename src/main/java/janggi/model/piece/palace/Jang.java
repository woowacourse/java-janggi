package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.piece.PieceType;

public class Jang extends PalacePiece {

    private Jang(
            Team team,
            PieceType pieceType
    ) {
        super(team, pieceType);
    }

    public Jang(
            Team team
    ) {
        super(team, PieceType.JANG);
    }
}
