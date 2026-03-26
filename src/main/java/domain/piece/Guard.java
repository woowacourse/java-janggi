package domain.piece;

import domain.PieceType;
import domain.Team;

public class Guard extends Piece{
    public Guard(Team team) {
        super(team, PieceType.GUARD);
    }
}
