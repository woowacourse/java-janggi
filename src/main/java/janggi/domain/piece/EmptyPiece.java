package janggi.domain.piece;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.Side;
import java.util.List;

public class EmptyPiece implements Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
    }

    public static EmptyPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Intersection from, Intersection to) {
        throw new UnsupportedOperationException("빈 객체는 이동할 수 없습니다.");
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        throw new UnsupportedOperationException("빈 객체는 이동할 수 없습니다.");
    }

    @Override
    public boolean isSameSide(Piece piece) {
        return false;
    }

    @Override
    public boolean isSameSide(Side side) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public boolean isSame(PieceType pieceType) {
        return PieceType.EMPTY == pieceType;
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public Side getSide() {
        throw new UnsupportedOperationException("빈 객체는 팀이 존재하지 않습니다.");
    }
}
