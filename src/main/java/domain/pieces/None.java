package domain.pieces;

import java.util.List;

import domain.Piece;
import domain.PieceType;
import domain.Position;

public class None extends Piece {
    public static final None INSTANCE = new None();

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public List<Position> getAvailablePositions(Position nowPosition) {
        return List.of(); // 아무 것도 못 움직임
    }
}
