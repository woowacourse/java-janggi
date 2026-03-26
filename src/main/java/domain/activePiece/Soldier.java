package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Soldier extends ActivePiece {
    protected Soldier(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
