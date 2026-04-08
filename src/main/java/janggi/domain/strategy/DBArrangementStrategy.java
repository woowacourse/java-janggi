package janggi.domain.strategy;

import janggi.domain.Location;
import janggi.domain.piece.Piece;
import java.util.Map;

public class DBArrangementStrategy implements ArrangementStrategy {

    private final Map<Location, Piece> pieces;

    public DBArrangementStrategy(Map<Location, Piece> pieces) {
        this.pieces = pieces;
    }

    @Override
    public void place(Piece[][] arrangement) {
        pieces.forEach((loc, piece) ->
                arrangement[loc.row()][loc.col()] = piece
        );
    }
}
