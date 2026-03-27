package domain.pieces;

import java.util.List;

import domain.PieceType;
import domain.Position;

public class None extends Piece {
    public static final None INSTANCE = new None();

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        return true;
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
