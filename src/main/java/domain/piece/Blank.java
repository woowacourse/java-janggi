package domain.piece;

import domain.PieceProvider;
import domain.Team;
import domain.position.Position;
import domain.strategy.NoStrategy;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE, new NoStrategy());
    }

    @Override
    public boolean canMove(Position from, Position to, PieceProvider pieceProvider) {
        return false;
    }
}
