package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Elephant extends ActivePiece {

    protected Elephant(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
