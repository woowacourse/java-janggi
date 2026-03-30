package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class King extends PalacePiece {
    public King(Team team) {
        super(team, PieceType.KING);
    }
}
