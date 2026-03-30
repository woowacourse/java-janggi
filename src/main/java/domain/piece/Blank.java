package domain.piece;

import domain.Team;
import domain.position.Position;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE);
    }

    @Override
    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        return false;
    }
}
