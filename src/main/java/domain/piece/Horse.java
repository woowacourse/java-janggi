package domain.piece;

import domain.PieceType;
import domain.Team;

public class Horse extends Piece {
    public Horse(Team team) {
        super(team, PieceType.HORSE);
    }
}
