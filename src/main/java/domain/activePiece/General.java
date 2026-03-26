package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class General extends ActivePiece {
    protected General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
