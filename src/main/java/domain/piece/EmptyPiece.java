package domain.piece;

import domain.Game;
import domain.Position;
import domain.Side;

import java.util.List;

public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super(Side.NEUTRAL);
    }

    @Override
    public List<Position> getPossibleMoves(Game game, Position start) {
        return List.of();
    }
}
