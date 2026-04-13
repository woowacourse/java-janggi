package domain.pieces;

import java.util.Collections;
import java.util.List;

import domain.PieceFinder;
import domain.enums.PieceType;
import domain.Position;

public class None extends Piece {
    public static final None INSTANCE = new None();

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        return Collections.emptyList();
    }

}
