package domain.piece;

import domain.PieceType;
import domain.Team;

public class Cannon extends Piece{
    public Cannon(Team team) {
        super(team, PieceType.CANNON);
    }
}
