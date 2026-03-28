package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.ActivePiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;

public class TestPiece extends ActivePiece {

    public TestPiece(Side side) {
        super(PieceType.NONE, side);
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
