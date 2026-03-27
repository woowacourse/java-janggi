package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public class EmptyPiece extends Piece {

    private static final String PIECE_NAME = "ㆍ";

    public EmptyPiece() {
        super(PIECE_NAME, Side.NONE);
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
