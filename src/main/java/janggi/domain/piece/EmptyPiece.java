package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

@SuppressWarnings("java:S6548")
public class EmptyPiece extends Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(PieceType.EMPTY, Side.NONE);
    }

    public static EmptyPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        throw new UnsupportedOperationException("빈 객체는 이동할 수 없습니다.");
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        throw new UnsupportedOperationException("빈 객체는 이동할 수 없습니다.");
    }
}
