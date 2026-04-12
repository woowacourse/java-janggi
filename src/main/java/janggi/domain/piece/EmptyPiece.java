package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

@SuppressWarnings("java:S6548")
public class EmptyPiece implements Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

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

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isPo() {
        throw new UnsupportedOperationException("빈 객체는 포일 수 없습니다.");
    }

    @Override
    public boolean isSameSide(Side side) {
        throw new UnsupportedOperationException("빈 객체는 진영이 존재하지 않습니다.");
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public Side getSide() {
        return Side.NONE;
    }
}
