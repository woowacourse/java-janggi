package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public class Cannon extends ActivePiece {

    public Cannon(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }
}
