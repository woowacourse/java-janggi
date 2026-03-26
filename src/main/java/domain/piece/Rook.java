package domain.piece;

import domain.PieceType;
import domain.Team;

public class Rook extends Piece{
    public Rook(Team team) {
        super(team, PieceType.ROOK);
    }
}
