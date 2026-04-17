package domain.piece;

import domain.Team;
import domain.strategy.PawnStrategy;

public class Pawn extends MoveablePiece {

    public Pawn(Team team) {
        super(team, PieceType.PAWN, new PawnStrategy());
    }
}
