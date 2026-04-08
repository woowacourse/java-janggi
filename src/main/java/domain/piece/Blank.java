package domain.piece;

import domain.Team;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE, PieceType.BLANK);
    }
}
