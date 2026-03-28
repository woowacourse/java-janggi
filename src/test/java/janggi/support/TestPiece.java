package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;

public class TestPiece extends Piece {

    private static final String PIECE_NAME = "test";

    public TestPiece(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return List.of();
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        return;
    }
}
