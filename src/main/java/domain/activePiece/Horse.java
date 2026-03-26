package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Horse extends ActivePiece {
    protected Horse(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
