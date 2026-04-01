package domain.piece;

import domain.PieceProvider;
import domain.Team;
import domain.position.Position;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE);
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        return false;
    }
}
