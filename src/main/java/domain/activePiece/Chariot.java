package domain.activePiece;


import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Chariot extends ActivePiece {

    protected Chariot(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }
}
