package domain.piece;

import domain.PieceType;
import domain.Team;

public class King extends Piece{
    public King(Team team) {
        super(team, PieceType.KING);
    }
}
