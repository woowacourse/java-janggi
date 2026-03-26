package domain.piece;

import domain.player.Team;

public class None extends Piece {

    public None() {
        super(Team.NULL, PieceType.NONE, null, null);
    }
}
