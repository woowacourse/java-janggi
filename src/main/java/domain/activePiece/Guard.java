package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Guard extends ActivePiece {
    protected Guard(Team team) {
        super(team, PieceType.SA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
