package janggi.strategy;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;

public class TeamPiece extends Piece {

    private static final String PIECE_NAME = "test";

    public TeamPiece(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return List.of();
    }
}
