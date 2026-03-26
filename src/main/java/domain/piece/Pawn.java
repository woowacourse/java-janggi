package domain.piece;

import domain.PieceType;
import domain.Team;

public class Pawn extends Piece{
    public Pawn(Team team) {
        super(team, PieceType.PAWN);
    }
}
