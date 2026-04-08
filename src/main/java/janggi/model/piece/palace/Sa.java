package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.piece.PieceType;

public class Sa extends PalacePiece {
    private Sa(
            Team team,
            PieceType pieceType
    ) {
        super(team, pieceType);
    }

    public Sa(Team team) {
        this(team, PieceType.SA);
    }
}
