package domain.piece;

import domain.position.Position;
import java.util.List;

public class EmptyPiece implements Piece {

    private EmptyPiece() {}

    private static class LazyHolder {
        private static final EmptyPiece INSTANCE = new EmptyPiece();
    }

    public static EmptyPiece getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        return List.of();
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public boolean isAlly(Piece other) {
        return false;
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorizeEmpty();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String toString() {
        return PieceType.EMPTY.name();
    }
}
